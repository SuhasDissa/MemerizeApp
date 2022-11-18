package app.suhasdissa.memerize.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import app.suhasdissa.memerize.utils.applySettingString
import app.suhasdissa.memerize.utils.getSettingString

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier
) {
    Column(modifier.fillMaxSize()) {
        val context = LocalContext.current
        Row(modifier.fillMaxWidth()) {
            var value by remember { mutableStateOf(getSettingString(context,"subreddit","tkasylum")) }

            TextField(value = value, onValueChange = { value = it })
            Button(onClick = { applySettingString(context,"subreddit",value)}) {
                Text("Save")
            }
        }
    }
}