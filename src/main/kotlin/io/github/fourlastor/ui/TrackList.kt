package io.github.fourlastor.ui

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.fourlastor.state.action.State
import java.io.File

@Composable
fun TrackList(state: State, modifier: Modifier, onFileSelected: (File) -> Unit) {
    LazyColumn(modifier = modifier, verticalArrangement = Arrangement.spacedBy(4.dp)) {
        items(state.files) { file ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onFileSelected(file) }
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column {
                    Text(text = file.name, fontSize = 16.sp, modifier = Modifier.padding(bottom = 4.dp))
                    Text(
                        "01:34",
                        color = MaterialTheme.colors.primaryVariant,
                        style = MaterialTheme.typography.body2
                    )
                }
            }
        }
    }
}

