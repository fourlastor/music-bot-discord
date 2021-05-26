package io.github.fourlastor.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.fourlastor.ui.FileTree

@Composable
fun FileItemIcon(modifier: Modifier, model: FileTree.Item) = Box(modifier.size(24.dp).padding(4.dp)) {
    when {
        !model.canExpand -> Unit
        model.isExpanded -> Icon(
            Icons.Default.KeyboardArrowDown, contentDescription = null, tint = LocalContentColor.current
        )
        else -> Icon(
            Icons.Default.KeyboardArrowRight, contentDescription = null, tint = LocalContentColor.current
        )
    }
}
