package io.github.spokesugar.kmp.todo.data.repository

import androidx.paging.PagingSource
import io.github.spokesugar.kmp.todo.data.database.dao.TodoDao
import io.github.spokesugar.kmp.todo.data.database.entities.Todo
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.Clock
import org.koin.core.annotation.Single

@Single(binds = [TodoRepository::class])
class TodoRoomRepository(private val dao: TodoDao) : TodoRepository {
    override suspend fun insert(title: String, description: String, isCompleted: Boolean): Long =
        dao.insert(
            todo = Todo(
                id = 0,
                title = title,
                description = description,
                isCompleted = isCompleted,
                updatedAt = Clock.System.now(),
                createdAt = Clock.System.now()
            )
        )


    override suspend fun update(
        id: Long,
        title: String?,
        description: String?,
        isCompleted: Boolean?
    ) {
        dao.update(id = id, title = title, description = description, isCompleted = isCompleted)
    }

    override suspend fun delete(id: Long) = dao.delete(id)

    override fun getTodoPages(): PagingSource<Int, Todo> = dao.pagingAll()

    override fun getByIdFlow(id: Long): Flow<Todo> = dao.getByIdFlow(id)

    override suspend fun getById(id: Long): Todo = dao.getById(id)

}