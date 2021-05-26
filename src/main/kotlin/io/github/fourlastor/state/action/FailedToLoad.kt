package io.github.fourlastor.state.action

import io.github.fourlastor.state.action.Action
import io.github.fourlastor.state.action.State


class FailedToLoad(fileName: String) : Action {
    override fun invoke(state: State): State {
        return state
    }
}

