package io.github.spokesugar.kmp.todo.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import io.github.spokesugar.kmp.todo.ui.viewmodel.ListColumnViewModel
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreenList(
    onClick: (value: Long) -> Unit,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    viewModel: ListColumnViewModel = koinViewModel()
) {
    val items = viewModel.todoColumns.collectAsLazyPagingItems()

    LazyColumn(contentPadding = contentPadding) {
        if (items.loadState.refresh is LoadState.Loading) {
            item {
                CircularProgressIndicator()
            }
        }

        items(
            count = items.itemCount,
            key = items.itemKey { it.id.toInt() },
        ) { index ->
            val item = items[index]!!

            val dismissState = rememberSwipeToDismissBoxState(
                confirmValueChange = { dismissValue ->
                    if (dismissValue == SwipeToDismissBoxValue.EndToStart) {
                        viewModel.deleteTodo(item.id)
                        true
                    } else {
                        false
                    }
                },
                positionalThreshold = { distance: Float ->
                    distance
                }
            )
            SwipeToDismissBox(
                state = dismissState,
                enableDismissFromEndToStart = true,
                enableDismissFromStartToEnd = false,
                backgroundContent = {
                    val color by animateColorAsState(
                        targetValue = when (dismissState.dismissDirection) {
                            SwipeToDismissBoxValue.Settled -> Color.Transparent
                            else -> MaterialTheme.colorScheme.error
                        }
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color)
                            .padding(horizontal = 20.dp),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete",
                            tint = MaterialTheme.colorScheme.onError
                        )
                    }
                },
            ) {
                ListItem(
                    modifier = Modifier.combinedClickable(
                        onClick = {
                            onClick(item.id)
                        }
                    ),
                    headlineContent = {
                        Text(
                            item.title,
                            maxLines = 2,
                            style = LocalTextStyle.current.copy(textDecoration = item.isCompleted.getLineThroughTextDecoration)
                        )
                    },
                    supportingContent = if (item.description != "") {
                        { Text(item.description, maxLines = 1) }
                    } else null,
                    trailingContent = {
                        Checkbox(
                            checked = item.isCompleted,
                            onCheckedChange = { viewModel.updateToggles(item.id, it) }
                        )
                    }
                )

                if (dismissState.currentValue == SwipeToDismissBoxValue.EndToStart) {
                    LaunchedEffect(Unit) {
                        viewModel.deleteTodo(id = item.id)
                    }
                }
            }
        }
        if (items.loadState.append is LoadState.Loading) {
            item {
                CircularProgressIndicator()
            }
        }
    }

}