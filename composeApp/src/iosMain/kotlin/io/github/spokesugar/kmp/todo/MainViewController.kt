package io.github.spokesugar.kmp.todo

import androidx.compose.ui.window.ComposeUIViewController
import io.github.spokesugar.kmp.todo.data.database.DatabaseBuilderHelper
import io.github.spokesugar.kmp.todo.di.defaultModule
import io.github.spokesugar.kmp.todo.di.roomModule
import io.github.spokesugar.kmp.todo.ui.App
import org.koin.core.context.startKoin
import org.koin.dsl.module

fun doInitKoin() {
    startKoin {
        module {
            modules(defaultModule, roomModule)
            single<DatabaseBuilderHelper> { DatabaseBuilderHelper() }
        }
    }
}

fun MainViewController() = ComposeUIViewController {
    App()
}