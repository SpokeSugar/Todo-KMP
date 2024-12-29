package io.github.spokesugar.kmp.todo.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.github.spokesugar.kmp.todo.ui.viewmodel.ListDetailViewModel
import io.github.spokesugar.kmp.todo.ui.viewmodel.LoadingState
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

internal val Boolean.getLineThroughTextDecoration: TextDecoration
    get() = if (this) TextDecoration.LineThrough else TextDecoration.None

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenDetail(
    id: Long,
    showBackButton: Boolean,
    onBack: () -> Unit,
    viewModel: ListDetailViewModel = koinViewModel(key = id.toString()) {
        parametersOf(
            id
        )
    }
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        topBar = if (showBackButton) ({
            TopAppBar(
                {},
                navigationIcon = {
                    IconButton({
                        onBack()
                    }) {
                        Icon(Icons.AutoMirrored.Default.ArrowBack, "Back")
                    }
                })
        }) else ({})
    ) { innerPadding ->
        Box(
            modifier = Modifier.padding(innerPadding),
        ) {
            when (state) {
                is LoadingState.Failure -> {
                    Text(
                        (state as LoadingState.Failure).exception.message ?: "Unknown Error",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                LoadingState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }

                is LoadingState.Success -> {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        TextField(
                            label = { Text("Title") },
                            trailingIcon = {
                                Checkbox(
                                    checked = viewModel.isComplete,
                                    onCheckedChange = { viewModel.isComplete = it }
                                )
                            },
                            modifier = Modifier.fillMaxWidth(),
                            textStyle = LocalTextStyle.current.copy(
                                textDecoration = viewModel.isComplete.getLineThroughTextDecoration
                            ),
                            readOnly = viewModel.isComplete,
                            value = viewModel.title,
                            onValueChange = { viewModel.title = it },
                        )

                        TextField(
                            label = { Text("Description") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .verticalScroll(rememberScrollState()),
                            readOnly = viewModel.isComplete,
                            value = viewModel.description,
                            onValueChange = { viewModel.description = it },
                            minLines = 5,
                            maxLines = 10,
                        )
                    }
                }

                LoadingState.Saving -> {
                    CircularProgressIndicator()
                }

            }
        }
    }
}