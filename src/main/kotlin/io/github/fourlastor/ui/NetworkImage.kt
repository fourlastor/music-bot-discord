package io.github.fourlastor.ui

import androidx.compose.runtime.*
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.jetbrains.skija.Image
import ru.gildor.coroutines.okhttp.await
import java.io.IOException

@Suppress("BlockingMethodInNonBlockingContext") // false positive
@Composable
fun NetworkImage(url: String, content: @Composable (ImageBitmap?) -> Unit) {
    val imageBitmap by produceState<ImageBitmap?>(null, url) {
        withContext(Dispatchers.IO) {
            val request = Request.Builder()
                    .get()
                    .url(url)
                    .build()

            val client = OkHttpClient.Builder().build()

            val body = try {
                client.newCall(request)
                        .await()
                        .body
            } catch (e: IOException) {
                value = null
                return@withContext
            }

            value = body?.bytes()?.toImageBitmap()
        }
    }

    content(imageBitmap)
}

private fun ByteArray.toImageBitmap(): ImageBitmap {
    return this.let { Image.makeFromEncoded(it).asImageBitmap() }
}
