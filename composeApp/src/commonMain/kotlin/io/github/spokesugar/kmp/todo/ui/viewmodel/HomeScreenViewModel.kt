package io.github.spokesugar.kmp.todo.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.spokesugar.kmp.todo.domain.AddTodoUseCase
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class HomeScreenViewModel(val addTodoUseCase: AddTodoUseCase) : ViewModel() {
    fun createNewScreen(callback: (value: Long) -> Unit) {
        viewModelScope.launch {
            val id = addTodoUseCase(
                title = "",
                description = "",
                isCompleted = false
            )

            callback(id)
        }
    }
}