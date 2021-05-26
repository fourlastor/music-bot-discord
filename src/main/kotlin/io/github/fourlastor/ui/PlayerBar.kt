package io.github.fourlastor.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorXmlResource
import androidx.compose.ui.unit.dp

@Composable
fun PlayerBar(onPlay: () -> Unit) {
    Surface(elevation = 4.dp) {
        Row(
            modifier = Modifier
                .background(Color.LightGray)
                .height(64.dp)
                .fillMaxWidth()
                .padding(4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            PlayerIcon(vectorXmlResource("ic_pause.xml"), onClick = onPlay)
        }
    }
}

@Composable
private fun PlayerIcon(icon: ImageVector, onClick: () -> Unit = {}) {
    Icon(
        imageVector = icon,
        contentDescription = null,
        modifier = Modifier.clickable { onClick() }.size(48.dp).padding(4.dp),
    )
}
