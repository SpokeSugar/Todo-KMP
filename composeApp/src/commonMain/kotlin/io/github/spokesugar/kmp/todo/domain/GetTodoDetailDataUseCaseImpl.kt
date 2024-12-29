package io.github.spokesugar.kmp.todo.domain

import io.github.spokesugar.kmp.todo.data.database.entities.Todo
import io.github.spokesugar.kmp.todo.data.repository.TodoRepository
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Single
import org.koin.core.logger.Logger

@Single([GetTodoDetailDataUseCase::class])
class GetTodoDetailDataUseCaseImpl(private val todoRepository: TodoRepository,private val logger: Logger?) : GetTodoDetailDataUseCase {

    override fun invoke(id: Long): Flow<Todo> {
        logger?.debug("${this::class.simpleName}: invoke")
        return todoRepository.getByIdFlow(id)
    }
}