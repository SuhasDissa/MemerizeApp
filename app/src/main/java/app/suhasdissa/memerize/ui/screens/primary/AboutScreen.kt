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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import app.suhasdissa.memerize.R
import app.suhasdissa.memerize.utils.checkUpdate
import app.suhasdissa.memerize.utils.openBrowser
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun AboutScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    Column(
        modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {

        Text(
            stringResource(R.string.developer_heading),
            style = MaterialTheme.typography.headlineLarge
        )
        Card(modifier.fillMaxWidth()) {
            Row(
                modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    modifier = modifier
                        .size(120.dp)
                        .clip(CircleShape),
                    model = ImageRequest.Builder(context = LocalContext.current)
                        .data(stringResource(R.string.github_avatar)).crossfade(true).build(),
                    contentDescription = stringResource(R.string.avatar_description)
                )
                Column(
                    modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(R.string.developer_name),
                        style = MaterialTheme.typography.headlineMedium
                    )
                    Text(
                        text = stringResource(R.string.developer_username),
                        style = MaterialTheme.typography.headlineSmall
                    )
                    Row(modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                        IconButton(onClick = {
                            openBrowser(
                                context, "https://github.com/SuhasDissa"
                            )
                        }) {

                            Icon(
                                painterResource(R.drawable.ic_github),
                                contentDescription = stringResource(R.string.github_icon_hint),
                                modifier.size(40.dp)
                            )
                        }
                        IconButton(onClick = {
                            openBrowser(
                                context, "https://twitter.com/SuhasDissa"
                            )
                        }) {

                            Icon(
                                painterResource(R.drawable.ic_twitter),
                                contentDescription = stringResource(R.string.twitter_icon_hint),
                                modifier.size(40.dp)
                            )
                        }
                    }
                }
            }
        }
        Card(
            modifier.fillMaxWidth()
        ) {
            Row(
                modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.update_description),
                    style = MaterialTheme.typography.headlineSmall
                )
                Button(onClick = { checkUpdate(context) }, enabled = false) {
                    Text(
                        stringResource(R.string.update_btn),
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
            }
        }
    }
}