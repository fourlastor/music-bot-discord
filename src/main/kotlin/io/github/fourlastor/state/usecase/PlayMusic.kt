package io.github.fourlastor.state.usecase

import io.github.fourlastor.music.MusicBot
import io.github.fourlastor.state.action.Action
import io.github.fourlastor.state.action.FailedToLoad
import io.github.fourlastor.state.action.TrackLoading
import io.github.fourlastor.state.action.TrackPlaying
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import net.dv8tion.jda.api.entities.Guild

@ExperimentalCoroutinesApi
class PlayMusic(private val bot: MusicBot) {
    operator fun invoke(guild: Guild, fileName: String): Flow<Action> = flow {
        emit(TrackLoading(fileName))
        val trackPlayed = bot.play(guild, fileName)

        if (trackPlayed) {
            TrackPlaying(fileName)
        } else {
            FailedToLoad(fileName)
        }
            .also { emit(it) }
    }
}
