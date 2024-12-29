package io.github.spokesugar.kmp.todo.domain

import androidx.paging.PagingSource
import io.github.spokesugar.kmp.todo.data.database.entities.Todo

interface GetTodoPagingDataUseCase {
    operator fun invoke(): PagingSource<Int,Todo>
}