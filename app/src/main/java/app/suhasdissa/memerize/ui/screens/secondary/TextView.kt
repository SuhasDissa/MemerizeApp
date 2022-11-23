package app.suhasdissa.memerize.ui.screens.secondary

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@Composable
fun TextView(text: String, modifier: Modifier = Modifier) {
    val decodedText = URLDecoder.decode(text, StandardCharsets.UTF_8.toString())
    Box(modifier.fillMaxSize(), Alignment.Center) {
        Card(
            modifier
                .fillMaxSize(.8f)
                .padding(10.dp)
        ) {
            LazyColumn(modifier.fillMaxSize()) {
                item {
                    SelectionContainer(modifier.fillMaxSize()) {
                        Text(decodedText, style = MaterialTheme.typography.bodyLarge)
                    }
                }
            }
        }
    }
}