package io.github.fourlastor.state

import io.github.fourlastor.state.action.Action
import io.github.fourlastor.state.action.AddGuild
import io.github.fourlastor.state.action.State
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.events.guild.GuildReadyEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import javax.inject.Inject

class MusicApp @Inject constructor(private val jdaBuilder: JDABuilder) {
    private val actions = MutableSharedFlow<Action>()
    private val _state = MutableStateFlow(State.empty())

    val state: StateFlow<State> = _state

    fun start() = GlobalScope.launch {
        val jda = jdaBuilder
            .addEventListeners(object : ListenerAdapter() {
                override fun onGuildReady(event: GuildReadyEvent) {
                    update(AddGuild(event.guild))
                }
            })
            .build()
        try {
            actions.collect { _state.value = it.invoke(_state.value) }
        } finally {
            jda.shutdown()
        }
    }

    fun update(action: Action) = GlobalScope.launch {
        actions.emit(action)
    }

    fun update(actions: Flow<Action>) = GlobalScope.launch {
        actions.collect { update(it) }
    }
}
