package io.github.spokesugar.kmp.todo.data.database.entities

class UpdateTodoData(
    val id: Long,
    val title: String? = null,
    val description: String? = null,
    val isCompleted: Boolean? = null,
)