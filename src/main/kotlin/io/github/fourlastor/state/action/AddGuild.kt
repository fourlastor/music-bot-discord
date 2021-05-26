package io.github.fourlastor.state.action

import net.dv8tion.jda.api.entities.Guild

class AddGuild(private val guild: Guild) : Action {
    override fun invoke(state: State) = state.copy(guilds = state.guilds.plus(guild))
}
