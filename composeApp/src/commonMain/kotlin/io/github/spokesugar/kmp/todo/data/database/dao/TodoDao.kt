package io.github.spokesugar.kmp.todo.data.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import io.github.spokesugar.kmp.todo.data.database.entities.Todo
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

@Dao
interface TodoDao {
    @Insert
    suspend fun insert(todo: Todo) :Long

    @Update
    suspend fun update(todo: Todo)

    @Query(
        """UPDATE Todo 
                SET isCompleted =(CASE WHEN :isCompleted IS NOT NULL THEN :isCompleted ELSE isCompleted END),
                    title = (CASE WHEN :title IS NOT NULL THEN :title ELSE title END),
                    description = (CASE WHEN :description IS NOT NULL THEN :description ELSE description END),
                    isCompleted = (CASE WHEN :isCompleted IS NOT NULL THEN :isCompleted ELSE isCompleted END),
                    updatedAt = :updateAt
                WHERE id = :id
                """
    )
    suspend fun update(
        id: Long,
        title: String? = null,
        description: String? = null,
        isCompleted: Boolean? = null,
        updateAt: Instant = Clock.System.now()
    )

    @Query("UPDATE Todo SET isCompleted = :isCompleted, updatedAt = :updatedAt WHERE id = :id")
    suspend fun toggleTodo(id: Long, isCompleted: Boolean,updatedAt: Instant = Clock.System.now())

    @Delete
    suspend fun delete(delete: Todo)

    @Query("DELETE FROM Todo WHERE id = :id")
    suspend fun delete(id: Long)

    @Query("SELECT * FROM Todo")
    fun pagingAll(): PagingSource<Int, Todo>

    @Query("SELECT * FROM Todo WHERE id = :id")
    fun getByIdFlow(id: Long): Flow<Todo>

    @Query("SELECT * FROM Todo WHERE id = :id")
    suspend fun getById(id: Long): Todo
}