package io.github.spokesugar.kmp.todo.data.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.SQLiteDriver
import androidx.sqlite.driver.AndroidSQLiteDriver
import org.koin.core.annotation.Single

@Single
actual class DatabaseBuilderHelper(private val context: Context) {
    actual fun getDatabaseBuilder(): RoomDatabase.Builder<AppDatabase> {
        val appContext = context.applicationContext
        val dbFile = appContext.getDatabasePath("app_database.db")

        return Room.databaseBuilder<AppDatabase>(
            context = appContext,
            name = dbFile.absolutePath,
        )
    }
}

actual val SQLiteDriver: SQLiteDriver = AndroidSQLiteDriver()