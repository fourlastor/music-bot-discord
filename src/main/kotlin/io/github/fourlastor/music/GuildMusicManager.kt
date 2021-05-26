package io.github.fourlastor.music

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter
import net.dv8tion.jda.api.audio.AudioSendHandler


/**
 * Holder for both the player and a track scheduler for one guild.
 */
class GuildMusicManager(manager: AudioPlayerManager) {
    /**
     * Audio player for the guild.
     */
    val player: AudioPlayer = manager.createPlayer()

    /**
     * Track scheduler for the player.
     */
    val scheduler: TrackScheduler = TrackScheduler(player)

    /**
     * @return Wrapper around AudioPlayer to use it as an AudioSendHandler.
     */
    val sendHandler: AudioPlayerSendHandler
        get() = AudioPlayerSendHandler(player)

    /**
     * Creates a player and a track scheduler.
     * @param manager Audio player manager to use for creating the player.
     */
    init {
        player.addListener(scheduler)
    }
}
