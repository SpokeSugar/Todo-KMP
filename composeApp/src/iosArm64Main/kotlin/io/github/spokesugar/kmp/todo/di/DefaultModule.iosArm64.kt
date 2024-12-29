package io.github.spokesugar.kmp.todo.di

import io.github.spokesugar.kmp.todo.data.database.RoomDatabaseModule
import org.koin.core.module.Module
import org.koin.ksp.generated.module

actual val defaultModule: Module = org.koin.ksp.generated.defaultModule

actual val roomModule: Module = RoomDatabaseModule().module