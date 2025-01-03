package io.github.spokesugar.kmp.todo.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext

@Composable
@Preview
fun App() {
    KoinContext{
        MaterialTheme {
            HomeScreen()
        }
    }
}