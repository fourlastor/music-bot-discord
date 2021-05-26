package io.github.fourlastor.state.action


class FailedToLoad(fileName: String) : Action {
    override fun invoke(state: State): State {
        return state
    }
}

