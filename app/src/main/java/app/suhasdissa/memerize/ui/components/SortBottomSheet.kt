/*******************************************************************************
Created By Suhas Dissanayake on 8/14/23, 1:10 PM
Copyright (c) 2023
https://github.com/SuhasDissa/
All Rights Reserved
 ******************************************************************************/

package app.suhasdissa.memerize.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import app.suhasdissa.memerize.R
import app.suhasdissa.memerize.backend.model.Sort

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SortBottomSheet(currentSort: Sort, onSelect: (Sort) -> Unit, onDismissRequest: () -> Unit) {
    val options = remember { listOf(Sort.Hot, Sort.New, Sort.Rising) }
    val topOptions = remember { listOf<Sort>(Sort.Top.Today, Sort.Top.Week, Sort.Top.Month) }

    ModalBottomSheet(onDismissRequest) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = stringResource(R.string.sort_by),
                style = MaterialTheme.typography.titleLarge
            )
            Row(Modifier.fillMaxWidth(), Arrangement.SpaceEvenly) {
                options.forEach {
                    SortFilterChip(selected = currentSort == it, sort = it, onSelect)
                }
            }
            Text(
                text = stringResource(id = R.string.top),
                style = MaterialTheme.typography.titleMedium
            )
            Row(Modifier.fillMaxWidth(), Arrangement.SpaceEvenly) {
                topOptions.forEach {
                    SortFilterChip(selected = currentSort == it, sort = it, onSelect)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SortFilterChip(selected: Boolean, sort: Sort, onSelect: (Sort) -> Unit) {
    FilterChip(
        selected,
        onClick = { onSelect(sort) },
        label = { Text(stringResource(id = sort.name)) }
    )
}
