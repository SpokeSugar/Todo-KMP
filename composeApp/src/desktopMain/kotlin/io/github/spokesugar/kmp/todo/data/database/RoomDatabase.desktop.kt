package io.github.spokesugar.kmp.todo.data.database

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.SQLiteDriver
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import org.koin.core.annotation.Single
import java.io.File

@Single
actual class DatabaseBuilderHelper {
    actual fun getDatabaseBuilder(): RoomDatabase.Builder<AppDatabase> {
        val dbFile = File(System.getProperty("user.dir"), "app_database.db")
        return Room.databaseBuilder<AppDatabase>(
            name = dbFile.absolutePath,
        )
    }
}

actual val SQLiteDriver: SQLiteDriver = BundledSQLiteDriver()