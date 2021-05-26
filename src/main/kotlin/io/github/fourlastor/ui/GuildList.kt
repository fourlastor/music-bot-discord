package io.github.fourlastor.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import net.dv8tion.jda.api.entities.Guild

@Composable
fun GuildList(guilds: Set<Guild>, selectedGuild: Guild?, onGuildSelected: (Guild) -> Unit) {
    Surface(elevation = 2.dp) {
        LazyColumn(modifier = Modifier.fillMaxHeight(), verticalArrangement = Arrangement.spacedBy(4.dp), contentPadding = PaddingValues(4.dp)) {
            items(guilds.toList()) { guild ->
                val guildIcon = guild.iconUrl ?: return@items
                NetworkImage(guildIcon) {
                    if (it == null) {
                        return@NetworkImage
                    }

                    val color = if (guild == selectedGuild) Color.Red else Color.DarkGray
                    Surface(
                            shape = RoundedCornerShape(4.dp),
                            modifier = Modifier
                                    .border(1.dp, color, RoundedCornerShape(4.dp))
                    ) {
                        Image(
                                bitmap = it,
                                contentDescription = null,
                                modifier = Modifier
                                        .size(64.dp)
                                        .clickable { onGuildSelected(guild) },
                                contentScale = ContentScale.Crop
                        )
                    }
                }
            }
        }
    }
}
