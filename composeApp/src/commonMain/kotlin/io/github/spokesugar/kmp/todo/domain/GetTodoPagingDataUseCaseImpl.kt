package io.github.spokesugar.kmp.todo.domain

import androidx.paging.PagingSource
import io.github.spokesugar.kmp.todo.data.database.entities.Todo
import io.github.spokesugar.kmp.todo.data.repository.TodoRepository
import org.koin.core.annotation.Single
import org.koin.core.logger.Logger

@Single(binds = [GetTodoPagingDataUseCase::class])
class GetTodoPagingDataUseCaseImpl(private val repository: TodoRepository,private val logger: Logger?) :
    GetTodoPagingDataUseCase {
    override fun invoke(): PagingSource<Int, Todo> {
        logger?.debug("${this::class.simpleName}: invoke")
        return repository.getTodoPages()
    }
}