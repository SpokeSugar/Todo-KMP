package io.github.spokesugar.kmp.todo.domain

import io.github.spokesugar.kmp.todo.data.repository.TodoRepository
import org.koin.core.annotation.Single
import org.koin.core.logger.Logger

@Single(binds = [AddTodoUseCase::class])
class AddTodoUseCaseImpl(private val repository: TodoRepository,private val logger: Logger?) : AddTodoUseCase {
    override suspend fun invoke(
        title: String,
        description: String,
        isCompleted: Boolean
    ): Long {
        logger?.debug("${this::class.simpleName}: Adding todo")
        return repository.insert(title, description, isCompleted)
    }

}