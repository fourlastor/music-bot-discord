package io.github.fourlastor.state.action

import net.dv8tion.jda.api.entities.Guild
import java.io.File

data class State(
    val files: List<File>,
    val guilds: Set<Guild>,
    val selectedGuild: Guild?
) {
    companion object {
        fun empty() = State(emptyList(), emptySet(), null)
    }
}

