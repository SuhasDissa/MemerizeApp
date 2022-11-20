package app.suhasdissa.memerize.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import app.suhasdissa.memerize.utils.applySettingString
import app.suhasdissa.memerize.utils.getSettingString

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        val context = LocalContext.current
        Text("Reddit Settings:", style = MaterialTheme.typography.headlineLarge)
        Text("Subreddit:", style = MaterialTheme.typography.headlineSmall)
        Row(modifier.fillMaxWidth().padding(10.dp) , horizontalArrangement = Arrangement.SpaceBetween) {
            var value by remember { mutableStateOf(getSettingString(context,"subreddit","tkasylum")) }

            TextField(value = value, onValueChange = { value = it })
            Button(onClick = { applySettingString(context,"subreddit",value)}, enabled = false) {
                Text("Save")
            }
        }
        Text("Meme Limit:", style = MaterialTheme.typography.headlineSmall)
        Row(modifier.fillMaxWidth().padding(10.dp) , horizontalArrangement = Arrangement.SpaceBetween) {
            var value by remember { mutableStateOf(getSettingString(context,"redditlimit","100")) }

            TextField(value = value, onValueChange = { value = it })
            Button(onClick = { applySettingString(context,"redditlimit",value)}, enabled = false) {
                Text("Save")
            }
        }

        Text("Telegram Settings:", style = MaterialTheme.typography.headlineLarge)
        Text("Channel:", style = MaterialTheme.typography.headlineSmall)
        Row(modifier.fillMaxWidth().padding(10.dp) , horizontalArrangement = Arrangement.SpaceBetween) {
            var value by remember { mutableStateOf(getSettingString(context,"tgchannel","chap_lin_sl")) }

            TextField(value = value, onValueChange = { value = it })
            Button(onClick = { applySettingString(context,"tgchannel",value)}, enabled = false) {
                Text("Save")
            }
        }
        Text("Meme Limit:", style = MaterialTheme.typography.headlineSmall)
        Row(modifier.fillMaxWidth().padding(10.dp) , horizontalArrangement = Arrangement.SpaceBetween) {
            var value by remember { mutableStateOf(getSettingString(context,"tglimit","20")) }

            TextField(value = value, onValueChange = { value = it })
            Button(onClick = { applySettingString(context,"tglimit",value)}, enabled = false) {
                Text("Save")
            }
        }
    }
}