package app.suhasdissa.memerize.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import app.suhasdissa.memerize.R
import app.suhasdissa.memerize.ui.utils.CheckUpdate
import app.suhasdissa.memerize.ui.utils.OpenBrowser
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun AboutScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    Column(modifier.fillMaxWidth().padding(horizontal = 10.dp), verticalArrangement = Arrangement.spacedBy(20.dp)) {

        Text("Developer:", style = MaterialTheme.typography.headlineLarge)
        Card(modifier.fillMaxWidth()) {
            Row(
                modifier.fillMaxWidth().padding(20.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    modifier = modifier
                        .size(120.dp)
                        .clip(CircleShape),
                    model = ImageRequest.Builder(context = LocalContext.current)
                        .data("https://avatars.githubusercontent.com/SuhasDissa").crossfade(true)
                        .build(),
                    contentDescription = "SuhasDissa"
                )
                Column(
                    modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Suhas Dissanayake", style = MaterialTheme.typography.headlineMedium
                    )
                    Text(
                        text = "@SuhasDissa", style = MaterialTheme.typography.headlineSmall
                    )
                    Row(modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                        IconButton(onClick = {
                            OpenBrowser(
                                context,
                                "https://github.com/SuhasDissa"
                            )
                        }) {

                            Icon(
                                painterResource(R.drawable.ic_github),
                                contentDescription = "Github", modifier.size(40.dp)
                            )
                        }
                        IconButton(onClick = {
                            OpenBrowser(
                                context,
                                "https://twitter.com/SuhasDissa"
                            )
                        }) {

                            Icon(
                                painterResource(R.drawable.ic_twitter),
                                contentDescription = "Twitter", modifier.size(40.dp)
                            )
                        }
                    }
                }
            }
        }
        Card(
            modifier
                .fillMaxWidth()) {
            val context = LocalContext.current
            Row(
                modifier.fillMaxWidth().padding(20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Check For Updates:", style = MaterialTheme.typography.headlineSmall
                )
                Button(onClick = { CheckUpdate(context) }) {
                    Text("UPDATE", style = MaterialTheme.typography.headlineSmall)
                }
            }
        }

    }
}