/*******************************************************************************
Created By Suhas Dissanayake on 11/23/22, 4:16 PM
Copyright (c) 2022
https://github.com/SuhasDissa/
All Rights Reserved
 ******************************************************************************/

package app.suhasdissa.memerize.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextCard(
    modifier: Modifier = Modifier,
    clickAction: () -> Unit,
    text: String
) {
    ElevatedCard(
        onClick = { clickAction() },
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()

    ) {
        Box(
            modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .height(IntrinsicSize.Min)
        ) {
            Text(text = text, style = MaterialTheme.typography.headlineSmall)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TextCardPreview(){
    TextCard(clickAction = {}, text = "Preview")
}