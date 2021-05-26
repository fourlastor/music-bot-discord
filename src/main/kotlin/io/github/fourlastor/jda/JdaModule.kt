package io.github.fourlastor.jda

import dagger.Module
import dagger.Provides
import io.github.fourlastor.configuration.BotKey
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.requests.GatewayIntent

@Module
object JdaModule {

    @Provides
    fun jdaBuilder(@BotKey botKey: String): JDABuilder = JDABuilder.create(
        botKey,
        GatewayIntent.GUILD_MESSAGES,
        GatewayIntent.GUILD_VOICE_STATES
    )
}
