import androidx.compose.desktop.DesktopTheme
import androidx.compose.desktop.Window
import androidx.compose.foundation.text.selection.DisableSelection
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import io.github.fourlastor.music.MusicBot
import io.github.fourlastor.state.action.State
import io.github.fourlastor.state.MusicApp
import io.github.fourlastor.state.action.AddFiles
import io.github.fourlastor.state.action.OnGuildSelected
import io.github.fourlastor.state.usecase.PlayMusic
import io.github.fourlastor.theme.AppTheme
import io.github.fourlastor.ui.MusicFilesDrop
import io.github.fourlastor.ui.MusicPlayerView
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.io.File

@ExperimentalCoroutinesApi
fun main() {

    val botKey = File("key.txt").readText().trim()

    val app = MusicApp(botKey)
    val bot = MusicBot()

    val playMusic = PlayMusic(bot)

    Window {
        DisposableEffect(true) {
            val appLifecycle = app.start()

            onDispose {
                appLifecycle.cancel()
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


