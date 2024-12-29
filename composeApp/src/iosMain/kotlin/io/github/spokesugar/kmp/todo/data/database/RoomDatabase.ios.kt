package io.github.spokesugar.kmp.todo.data.database

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.SQLiteDriver
import androidx.sqlite.driver.NativeSQLiteDriver
import kotlinx.cinterop.ExperimentalForeignApi
import org.koin.core.annotation.Single
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

@Single([DatabaseBuilderHelper::class])
actual class DatabaseBuilderHelper {
    actual fun getDatabaseBuilder(): RoomDatabase.Builder<AppDatabase> {
        val dbFilePath = documentDirectory() + "/my_room.db"
        return Room.databaseBuilder<AppDatabase>(
            name = dbFilePath,
        )
    }
}

@OptIn(ExperimentalForeignApi::class)
private fun documentDirectory(): String {
    val documentDirectory = NSFileManager.defaultManager().URLForDirectory(
        directory = NSDocumentDirectory,
        inDomain = NSUserDomainMask,
        appropriateForURL = null,
        create = false,
        error = null,
    )

    return requireNotNull(documentDirectory?.path)
}

actual val SQLiteDriver: SQLiteDriver = NativeSQLiteDriver()