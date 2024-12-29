package io.github.spokesugar.kmp.todo.domain

import io.github.spokesugar.kmp.todo.data.repository.TodoRepository
import org.koin.core.annotation.Single

@Single(binds = [UpdateTodoUseCase::class])
class UpdateTodoUseCaseImpl(
    private val todoRepository: TodoRepository,
) : UpdateTodoUseCase {
    override suspend fun invoke(
        id: Long,
        title: String?,
        description: String?,
        isCompleted: Boolean?
    ) {
        todoRepository.update(
            id = id,
            title = title,
            description = description,
            isCompleted = isCompleted
        )
    }
}