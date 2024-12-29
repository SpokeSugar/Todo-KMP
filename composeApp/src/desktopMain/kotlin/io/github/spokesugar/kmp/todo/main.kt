package io.github.spokesugar.kmp.todo

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import io.github.spokesugar.kmp.todo.data.database.RoomDatabaseModule
import io.github.spokesugar.kmp.todo.ui.App
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.ksp.generated.defaultModule
import org.koin.ksp.generated.module

fun main() {
    startKoin {
        modules(defaultModule, RoomDatabaseModule().module)
    }
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "KMP-TodoApp",
        ) {
            App()
        }
    }
}