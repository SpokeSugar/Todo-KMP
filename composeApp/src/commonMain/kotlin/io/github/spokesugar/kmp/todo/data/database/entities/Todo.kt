package io.github.spokesugar.kmp.todo.data.database.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.datetime.Instant


@Entity(
    indices = [
        Index(value = ["updatedAt"])
    ]
)
data class Todo(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val title: String,
    val description: String,
    val isCompleted: Boolean,
    val createdAt: Instant,
    val updatedAt: Instant
)