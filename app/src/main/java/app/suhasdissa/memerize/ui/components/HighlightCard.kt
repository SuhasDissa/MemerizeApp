package app.suhasdissa.memerize.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun HighlightCard(
    onClick: () -> Unit, modifier: Modifier = Modifier,
    image: Int,
    name: String
) {
    ElevatedCard(
        modifier = modifier
            .width(200.dp)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .clickable(onClick = { onClick() })
                .fillMaxSize(), verticalAlignment = Alignment.CenterVertically
        ) {

            Image(
                painter = painterResource(image),
                contentDescription = null,
                modifier = Modifier
                    .size(150.dp)
                    .clip(RoundedCornerShape(5.dp))
            )

            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = name,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }
}