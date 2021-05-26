package io.github.fourlastor

import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
fun main() {
    val app = DaggerAppComponent
        .create()
        .app()
    app.start()
}


