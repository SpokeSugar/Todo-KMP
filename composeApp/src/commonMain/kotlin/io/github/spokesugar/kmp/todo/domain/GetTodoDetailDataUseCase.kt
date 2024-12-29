package io.github.spokesugar.kmp.todo.domain

import io.github.spokesugar.kmp.todo.data.database.entities.Todo
import kotlinx.coroutines.flow.Flow

interface GetTodoDetailDataUseCase {
    operator fun invoke(id: Long): Flow<Todo>
}