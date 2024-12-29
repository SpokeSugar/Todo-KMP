package io.github.spokesugar.kmp.todo.data.database
import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.TypeConverters
import io.github.spokesugar.kmp.todo.data.database.dao.TodoDao
import io.github.spokesugar.kmp.todo.data.database.entities.Todo

@Database(entities = [Todo::class], version = 1)
@TypeConverters(Converter::class)
@ConstructedBy(AppDatabaseCtor::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getDao(): TodoDao
}

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object AppDatabaseCtor : RoomDatabaseConstructor<AppDatabase> {
    override fun initialize(): AppDatabase
}