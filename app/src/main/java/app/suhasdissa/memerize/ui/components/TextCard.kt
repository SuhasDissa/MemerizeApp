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
fun TextCard(
    modifier: Modifier = Modifier,
    clickAction: (url: String) -> Unit,
    text: String,
    clickUrl: String
) {
    ElevatedCard(
        onClick = { clickAction(clickUrl) },
        modifier= modifier
            .padding(4.dp)
            .fillMaxWidth()

    ) {
        Box(modifier.padding(horizontal = 10.dp ,vertical = 6.dp).height(IntrinsicSize.Min)) {
            Text(text = text, style = MaterialTheme.typography.headlineSmall)
        }
    }
}