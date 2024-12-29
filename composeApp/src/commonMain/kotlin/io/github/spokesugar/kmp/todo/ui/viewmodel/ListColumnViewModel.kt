package io.github.spokesugar.kmp.todo.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import io.github.spokesugar.kmp.todo.domain.GetTodoPagingDataUseCase
import io.github.spokesugar.kmp.todo.domain.RemoveTodoUseCase
import io.github.spokesugar.kmp.todo.domain.ToggleTodoUseCase
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class ListColumnViewModel(
    pagingDataUseCase: GetTodoPagingDataUseCase,
    val toggleUseCase: ToggleTodoUseCase,
    val removeTodoUseCase : RemoveTodoUseCase
) : ViewModel() {

    val todoColumns = Pager(config = PagingConfig(pageSize = 10, prefetchDistance = 20)) {
        pagingDataUseCase()
    }
        .flow

    fun updateToggles(id: Long, value: Boolean) {
        viewModelScope.launch {
            toggleUseCase(id, value)
        }
    }

    fun deleteTodo(id: Long) {
        viewModelScope.launch {
            removeTodoUseCase(id)
        }
    }
}