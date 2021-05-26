package io.github.fourlastor.state.action

interface Action {
    operator fun invoke(state: State): State
}
