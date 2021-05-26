package io.github.fourlastor

import dagger.Component
import io.github.fourlastor.configuration.ConfigurationModule
import io.github.fourlastor.jda.JdaModule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@Singleton
@Component(
    modules = [ConfigurationModule::class, JdaModule::class]
)
interface AppComponent {
    @ExperimentalCoroutinesApi
    fun app(): App
}
