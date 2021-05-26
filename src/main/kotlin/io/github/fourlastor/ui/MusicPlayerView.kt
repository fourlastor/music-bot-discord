package io.github.fourlastor.ui

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import io.github.fourlastor.state.action.State
import net.dv8tion.jda.api.entities.Guild
import java.io.File


@Composable
fun MusicPlayerView(state: State, onGuildSelected: (Guild) -> Unit, onFileSelected: (File) -> Unit, onPlay: () -> Unit) = Column {
    Row(modifier = Modifier.fillMaxWidth().weight(1f)) {
        GuildList(state.guilds, state.selectedGuild, onGuildSelected)
        TrackList(state = state, modifier = Modifier.weight(1f), onFileSelected)
    }
    PlayerBar(onPlay)
}

