package io.github.fourlastor.state

import io.github.fourlastor.state.action.Action
import io.github.fourlastor.state.action.State
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class MusicApp @Inject constructor() {
    private val actions = MutableSharedFlow<Action>()
    private val _state = MutableStateFlow(State.empty())

    val state: StateFlow<State> = _state

    fun start() = GlobalScope.launch {
        actions.collect { _state.value = it.invoke(_state.value) }
    }

    fun update(action: Action) = GlobalScope.launch {
        actions.emit(action)
    }

    fun update(actions: Flow<Action>) = GlobalScope.launch {
        actions.collect { update(it) }
    }
}
