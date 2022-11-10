package app.suhasdissa.memerize.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onClickMemeView: () -> Unit,
    onClickFunnyVideo: () -> Unit
) {
    Column(modifier = modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        ElevatedCard(
            onClick = { onClickMemeView() },
            modifier
                .fillMaxWidth(.98f)

        ) {
            Box(modifier.padding(horizontal = 10.dp ,vertical = 6.dp).height(IntrinsicSize.Min))  {
                Text(text = "Memes", style = MaterialTheme.typography.headlineMedium)
            }
        }

        ElevatedCard(
            onClick = { onClickFunnyVideo() },
            modifier
                .fillMaxWidth(.98f)

        ) {
            Box(modifier.padding(horizontal = 10.dp ,vertical = 6.dp).height(IntrinsicSize.Min))  {
                Text(text = "Funny Videos", style = MaterialTheme.typography.headlineMedium)
            }
        }
    }

}