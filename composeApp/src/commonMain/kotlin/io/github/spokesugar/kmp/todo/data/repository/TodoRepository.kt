package io.github.spokesugar.kmp.todo.data.repository

import androidx.paging.PagingSource
import io.github.spokesugar.kmp.todo.data.database.entities.Todo
import kotlinx.coroutines.flow.Flow

interface TodoRepository {
    suspend fun insert(title: String, description: String, isCompleted: Boolean):Long

    suspend fun update(
        id: Long,
        title: String? = null,
        description: String? = null,
        isCompleted: Boolean? = null
    )

    suspend fun delete(id: Long)
    fun getTodoPages(): PagingSource<Int,Todo>
    fun getByIdFlow(id: Long): Flow<Todo>
    suspend fun getById(id: Long) : Todo?
}