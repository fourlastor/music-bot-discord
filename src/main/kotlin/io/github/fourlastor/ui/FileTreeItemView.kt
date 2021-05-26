package io.github.fourlastor.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.input.pointer.pointerMoveFilter
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FileTreeItemView(fontSize: TextUnit = 14.sp, height: Dp, item: FileTree.Item, onClick: (FileTree.Item) -> Unit) =
    Row(
        modifier = Modifier
            .wrapContentHeight()
            .clickable { onClick(item) }
            .padding(start = 24.dp * item.level)
            .height(height)
    ) {

        val active = remember { mutableStateOf(false) }

        FileItemIcon(Modifier.align(Alignment.CenterVertically), item)
        Text(
            text = item.name,
            color = if (active.value) LocalContentColor.current.copy(alpha = 0.60f) else LocalContentColor.current,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .clipToBounds()
                .pointerMoveFilter(
                    onEnter = {
                        active.value = true
                        true
                    },
                    onExit = {
                        active.value = false
                        true
                    }
                ),
            softWrap = true,
            fontSize = fontSize,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
    }

class FileTree(
    val items: List<Item>
) {
    class Item(
        val name: String,
        val absolutePath: String,
        val canExpand: Boolean,
        val isExpanded: Boolean,
        val level: Int
    )
}
