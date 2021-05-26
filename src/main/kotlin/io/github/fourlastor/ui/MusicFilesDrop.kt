package io.github.fourlastor.ui

import androidx.compose.desktop.AppManager
import androidx.compose.runtime.Composable
import java.awt.datatransfer.DataFlavor
import java.awt.dnd.DnDConstants
import java.awt.dnd.DropTarget
import java.awt.dnd.DropTargetDropEvent
import java.io.File
import java.net.URLConnection

@Composable
fun MusicFilesDrop(onFilesDropped: (List<File>) -> Unit) {

    val target = object : DropTarget() {
        @Synchronized
        override fun drop(evt: DropTargetDropEvent) {
            evt.acceptDrop(DnDConstants.ACTION_REFERENCE)
            val droppedFiles = (evt.transferable.getTransferData(DataFlavor.javaFileListFlavor) as List<*>)
                .filterIsInstance<File>()
                .asSequence()
                .onlyMusic()
                .toList()

            onFilesDropped(droppedFiles)
        }
    }
    AppManager.windows.first().window.contentPane.dropTarget = target
}


fun Sequence<File>.onlyMusic(): Sequence<File> =
    filter { it.isDirectory || URLConnection.guessContentTypeFromName(it.name)?.startsWith("audio") == true }
        .flatMap { file ->
            if (file.isDirectory) {
                file.walkTopDown()
                    .maxDepth(1)
                    .filter { it !== file }
                    .onlyMusic()
            } else {
                sequenceOf(file)
            }
        }
