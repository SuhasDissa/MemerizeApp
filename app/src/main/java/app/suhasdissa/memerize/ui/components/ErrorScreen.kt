package app.suhasdissa.memerize.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import app.suhasdissa.memerize.R

@Composable
fun ErrorScreen(memeUiState: String, modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center, modifier = modifier.fillMaxSize()
    ) {
        Column() {
            Text(
                stringResource(R.string.loading_failed),
                style = MaterialTheme.typography.bodyLarge
            )
            Text(memeUiState, color = MaterialTheme.colorScheme.error)
        }
    }
}