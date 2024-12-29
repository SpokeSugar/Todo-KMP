package io.github.spokesugar.kmp.todo

import android.app.Application
import io.github.spokesugar.kmp.todo.data.database.DatabaseBuilderHelper
import io.github.spokesugar.kmp.todo.data.database.RoomDatabaseModule
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.ksp.generated.defaultModule
import org.koin.ksp.generated.module

class KMPTodoApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            modules(
                defaultModule,
                RoomDatabaseModule().module,
                module { single<DatabaseBuilderHelper> { DatabaseBuilderHelper(applicationContext) } }
            )
        }
    }
}