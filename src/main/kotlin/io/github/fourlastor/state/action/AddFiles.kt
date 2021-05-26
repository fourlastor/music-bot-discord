package io.github.fourlastor.state.action

import java.io.File

class AddFiles(private val files: List<File>) : Action {
    override fun invoke(state: State) = if (files.isEmpty()) {
        state
    } else state.copy(
        files = (state.files + files).distinctBy { it.absolutePath }
    )
}
