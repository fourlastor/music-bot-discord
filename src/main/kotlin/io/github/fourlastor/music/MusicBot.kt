package io.github.fourlastor.music

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist
import com.sedmelluq.discord.lavaplayer.track.AudioTrack
import kotlinx.coroutines.*
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.managers.AudioManager
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@Singleton
@ExperimentalCoroutinesApi
class MusicBot @Inject constructor() {
    private val playerManager: AudioPlayerManager
    private val musicManagers: MutableMap<Long, GuildMusicManager>

    init {
        musicManagers = HashMap()
        playerManager = DefaultAudioPlayerManager()
        AudioSourceManagers.registerRemoteSources(playerManager)
        AudioSourceManagers.registerLocalSource(playerManager)
    }

    @Synchronized
    private fun getGuildAudioPlayer(guild: Guild): GuildMusicManager {
        val guildId = guild.id.toLong()
        var musicManager = musicManagers[guildId]
        if (musicManager == null) {
            musicManager = GuildMusicManager(playerManager)
            musicManagers[guildId] = musicManager
        }
        guild.audioManager.sendingHandler = musicManager.sendHandler
        return musicManager
    }

    suspend fun play(guild: Guild, trackUrl: String) = suspendCoroutine<Boolean> { continuation ->
        val musicManager = getGuildAudioPlayer(guild)
        playerManager.loadItemOrdered(musicManager, trackUrl, object : AudioLoadResultHandler {
            override fun trackLoaded(track: AudioTrack) {
                play(guild, musicManager, track)
                continuation.resume(true)
            }

            override fun playlistLoaded(playlist: AudioPlaylist) {
                val firstTrack = playlist.selectedTrack ?: playlist.tracks[0]
                play(guild, musicManager, firstTrack)
                continuation.resume(true)
            }

            override fun noMatches() {
                continuation.resume(false)
            }

            override fun loadFailed(exception: FriendlyException) {
                continuation.resume(false)
            }
        })
    }

    fun loadAndPlay(guild: Guild, trackUrl: String) {
        val musicManager = getGuildAudioPlayer(guild)
        playerManager.loadItemOrdered(musicManager, trackUrl, object : AudioLoadResultHandler {
            override fun trackLoaded(track: AudioTrack) {
                play(guild, musicManager, track)
            }

            override fun playlistLoaded(playlist: AudioPlaylist) {
                val firstTrack = playlist.selectedTrack ?: playlist.tracks[0]
                play(guild, musicManager, firstTrack)
            }

            override fun noMatches() {
//                channel.sendMessage("Nothing found by $trackUrl").queue()
            }

            override fun loadFailed(exception: FriendlyException) {
//                channel.sendMessage("Could not play: " + exception.message).queue()
            }
        })
    }

    fun stop(guild: Guild) {
        val musicManager = getGuildAudioPlayer(guild)
        disconnectFromVoiceChannel(guild.audioManager)
        musicManager.scheduler.stop()
    }

    private fun play(guild: Guild, musicManager: GuildMusicManager, track: AudioTrack) {
        connectToFirstVoiceChannel(guild.audioManager)

        musicManager.scheduler.play(track)
    }

    private fun skipTrack(guild: Guild) {
        val musicManager = getGuildAudioPlayer(guild)
        musicManager.scheduler.nextTrack()
    }

    private fun connectToFirstVoiceChannel(audioManager: AudioManager) {
        if (!audioManager.isConnected) {
            audioManager.guild.voiceChannels
                .firstOrNull()
                ?.also { audioManager.openAudioConnection(it) }
        }
    }

    private fun disconnectFromVoiceChannel(audioManager: AudioManager) {
        audioManager.closeAudioConnection()
    }
}

