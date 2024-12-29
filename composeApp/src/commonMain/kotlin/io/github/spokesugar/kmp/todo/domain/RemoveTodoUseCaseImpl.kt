package io.github.spokesugar.kmp.todo.domain

import io.github.spokesugar.kmp.todo.data.database.dao.TodoDao
import org.koin.core.annotation.Single

@Single(binds = [RemoveTodoUseCase::class])
class RemoveTodoUseCaseImpl(private val dao: TodoDao) : RemoveTodoUseCase {
    override suspend fun invoke(id: Long) {
        dao.delete(id)
    }

}