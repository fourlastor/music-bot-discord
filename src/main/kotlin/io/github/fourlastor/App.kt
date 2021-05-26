package io.github.fourlastor

import androidx.compose.desktop.DesktopTheme
import androidx.compose.desktop.Window
import androidx.compose.foundation.text.selection.DisableSelection
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import io.github.fourlastor.music.MusicBot
import io.github.fourlastor.state.MusicApp
import io.github.fourlastor.state.action.AddFiles
import io.github.fourlastor.state.action.AddGuild
import io.github.fourlastor.state.action.OnGuildSelected
import io.github.fourlastor.state.action.State
import io.github.fourlastor.state.usecase.PlayMusic
import io.github.fourlastor.theme.AppTheme
import io.github.fourlastor.ui.MusicFilesDrop
import io.github.fourlastor.ui.MusicPlayerView
import kotlinx.coroutines.ExperimentalCoroutinesApi
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.events.guild.GuildReadyEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import javax.inject.Inject
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Singleton
class App @Inject constructor(
    private val app: MusicApp,
    private val bot: MusicBot,
    private val playMusic: PlayMusic,
    private val jdaBuilder: JDABuilder,
) {
    fun start() {
        Window {
            DisposableEffect(true) {
                val appLifecycle = app.start()
                val jda = jdaBuilder
                    .addEventListeners(object : ListenerAdapter() {
                        override fun onGuildReady(event: GuildReadyEvent) {
                            app.update(AddGuild(event.guild))
                        }
                    })
                    .build()

                onDispose {
                    appLifecycle.cancel()
                    jda.shutdown()
                }
            }

            val musicPlayer = app.state.collectAsState(State.empty())

            DisableSelection {
                MaterialTheme(
                    colors = AppTheme.colors.material
                ) {
                    DesktopTheme {
                        MusicPlayerView(
                            state = musicPlayer.value,
                            onFileSelected = {
                                val selectedGuild = app.state.value.selectedGuild ?: return@MusicPlayerView
                                app.update(playMusic(selectedGuild, it.absolutePath))
                            },
                            onGuildSelected = { app.update(OnGuildSelected(it)) },
                            onPlay = {
                                val selectedGuild = app.state.value.selectedGuild ?: return@MusicPlayerView
                                bot.stop(selectedGuild)
                            }
                        )
                        MusicFilesDrop(
                            onFilesDropped = {
                                app.update(AddFiles(it))
                            }
                        )
                    }
                }
            }
        }
    }
}
