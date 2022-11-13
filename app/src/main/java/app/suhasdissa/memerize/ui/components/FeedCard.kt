package app.suhasdissa.memerize.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedCard(
    modifier: Modifier = Modifier,
    topic: String,
    content: String
) {
    ElevatedCard(
        modifier = modifier
            .padding(4.dp)
            .fillMaxWidth()

    ) {
        Column(
            modifier
                .padding(horizontal = 10.dp, vertical = 6.dp)
                .height(IntrinsicSize.Min)) {
            Text(text = topic, style = MaterialTheme.typography.headlineSmall)
            Text(text = content)
        }
    }
}