package io.github.spokesugar.kmp.todo.data.database

import androidx.room.TypeConverter
import kotlinx.datetime.Instant

object Converter {
    @TypeConverter
    fun fromInstant(value: Long?): Instant? = value?.let { Instant.fromEpochMilliseconds(it) }

    @TypeConverter
    fun toInstant(value: Instant?): Long? = value?.toEpochMilliseconds()
}