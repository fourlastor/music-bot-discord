package io.github.fourlastor.configuration

import dagger.Module
import dagger.Provides
import java.io.File
import javax.inject.Qualifier

@Module
object ConfigurationModule {
    @Provides
    @BotKey
    fun botKey(): String = File("key.txt").readText().trim()
}

@Qualifier
annotation class BotKey
