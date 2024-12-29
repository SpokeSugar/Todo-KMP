package io.github.spokesugar.kmp.todo.data.database

import androidx.room.RoomDatabase
import androidx.sqlite.SQLiteDriver
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import io.github.spokesugar.kmp.todo.data.database.dao.TodoDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Provided
import org.koin.core.annotation.Single

@Module
@ComponentScan("io.github.spokesugar.kmp.todo.data.database")
class RoomDatabaseModule {

    @Single([AppDatabase::class])
    fun injectAppDatabase(@Provided helper: DatabaseBuilderHelper) = helper
        .getDatabaseBuilder()
        .addMigrations()
        .fallbackToDestructiveMigrationOnDowngrade(
            dropAllTables = false
        )
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()

    @Single([TodoDao::class])
    fun injectTodoDao(appDatabase: AppDatabase) = appDatabase.getDao()
}

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class DatabaseBuilderHelper {
    fun getDatabaseBuilder(): RoomDatabase.Builder<AppDatabase>
}

expect val SQLiteDriver: SQLiteDriver