package io.github.spokesugar.kmp.todo.ui

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.layout.PaneAdaptedValue
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import io.github.spokesugar.kmp.todo.ui.entities.TodoColumnID
import io.github.spokesugar.kmp.todo.ui.viewmodel.HomeScreenViewModel
import io.github.spokesugar.kmp.todo.util.BackHandler
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun HomeScreen(homeScreenViewModel: HomeScreenViewModel = koinViewModel()) {

    val navigator = rememberListDetailPaneScaffoldNavigator<TodoColumnID>()

    BackHandler(navigator.canNavigateBack()) {
        navigator.navigateBack()
    }

    ListDetailPaneScaffold(directive = navigator.scaffoldDirective,
        value = navigator.scaffoldValue,
        listPane = {
            AnimatedPane {
                Scaffold(floatingActionButton = {
                    FloatingActionButton(onClick = {
                        homeScreenViewModel.createNewScreen {
                            navigator.navigateTo(
                                ListDetailPaneScaffoldRole.Detail,
                                TodoColumnID(it)
                            )

                        }
                    }) {
                        Icon(Icons.Filled.Add, contentDescription = "Add")
                    }
                }) {
                    HomeScreenList(
                        onClick = { item ->
                            navigator.navigateTo(
                                ListDetailPaneScaffoldRole.Detail,
                                TodoColumnID(item)
                            )
                        },
                        contentPadding = WindowInsets.systemBars.asPaddingValues()
                    )
                }
            }
        },
        detailPane = {
            val itemIndex =
                remember(navigator.currentDestination?.content?.long) { navigator.currentDestination?.content?.long }

            Scaffold {
                if (itemIndex != null) {
                    HomeScreenDetail(
                        itemIndex,
                        showBackButton = navigator.scaffoldValue.secondary == PaneAdaptedValue.Hidden,
                        onBack = { navigator.navigateBack() }
                    )
                }
            }
        }

    )
}
