package io.github.spokesugar.kmp.todo.domain

import io.github.spokesugar.kmp.todo.data.repository.TodoRepository
import org.koin.core.annotation.Single
import org.koin.core.logger.Logger

@Single(binds = [ToggleTodoUseCase::class])
class ToggleTodoUseCaseImpl(private val todoRepository: TodoRepository,private val logger: Logger?): ToggleTodoUseCase {
    override suspend operator fun invoke(id: Long, isCompleted: Boolean) {
        logger?.debug("${this::class.simpleName}: invoke")
        todoRepository.update(id, isCompleted = isCompleted)
    }
}