package io.github.fourlastor.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.fourlastor.ui.FileTree

@Composable
fun DirectoryList(fileTree: FileTree, onClick: (FileTree.Item) -> Unit) {
    LazyColumn {
        items(fileTree.items.size) {
            val item = fileTree.items[it]
            FileTreeItemView(14.sp, 14.dp * 1.5f, item, onClick)
        }
    }
}
