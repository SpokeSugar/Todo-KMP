package io.github.spokesugar.kmp.todo.domain.entities

import kotlinx.datetime.Instant

data class Todo(
    val id: Long,
    val title: String,
    val description: String,
    val isCompleted: Boolean,
    val createdAt: Instant,
    val updatedAt: Instant
)