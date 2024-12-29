package io.github.spokesugar.kmp.todo.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.spokesugar.kmp.todo.data.database.entities.Todo
import io.github.spokesugar.kmp.todo.data.database.entities.UpdateTodoData
import io.github.spokesugar.kmp.todo.domain.GetTodoDetailDataUseCase
import io.github.spokesugar.kmp.todo.domain.UpdateTodoUseCase
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.InjectedParam
import kotlin.coroutines.CoroutineContext

sealed interface LoadingState {
    val isLoading: Boolean
    val success: Boolean
    val isFinished: Boolean

    data object Loading : LoadingState {
        override val isLoading = true
        override val success = false
        override val isFinished = false
    }

    data class Success(val todo: Todo) : LoadingState {
        override val isLoading = false
        override val success = true
        override val isFinished = true
    }

    data class Failure(val exception: Throwable) : LoadingState {
        override val isLoading = false
        override val success = false
        override val isFinished = true
    }

    data object Saving : LoadingState {
        override val isLoading: Boolean = true
        override val success: Boolean = false
        override val isFinished: Boolean = false
    }
}


@KoinViewModel
class ListDetailViewModel(
    @InjectedParam private val id: Long,
    private val updateTodoUseCase: UpdateTodoUseCase,
    private val getTodoDetailDataUseCase: GetTodoDetailDataUseCase,
    private val dispatcher: CoroutineContext = Dispatchers.IO
) : ViewModel() {
    var title by mutableStateOf("")

    var description by mutableStateOf("")

    var isComplete by mutableStateOf(false)

    private val mutableStateFlow = MutableStateFlow<LoadingState>(LoadingState.Loading)
    val state = mutableStateFlow.asStateFlow()

    private val sendMessageFlow: SharedFlow<UpdateTodoData> = snapshotFlow {
        UpdateTodoData(
            id = id,
            title = title,
            description = description,
            isCompleted = isComplete
        )
    }
        .filterNot { state.value is LoadingState.Loading }
        .shareIn(viewModelScope, started = SharingStarted.Eagerly)

    init {
        viewModelScope.launch(dispatcher) {
            val first = getTodoDetailDataUseCase(id)
                .map { LoadingState.Success(it) }
                .filterIsInstance(LoadingState::class)
                .catch { emit(LoadingState.Failure(it)) }
                .first()

            if (first is LoadingState.Success) {
                title = first.todo.title
                description = first.todo.description
                isComplete = first.todo.isCompleted
            }

            mutableStateFlow.emit(first)
        }

        sendMessageFlow
            .distinctUntilChanged()
            .conflate()
            .transform {
                emit(it)
                delay(100L)
            }
            .onEach { flow ->
                updateTodoUseCase(id, flow.title, flow.description, flow.isCompleted)
            }
            .flowOn(dispatcher)
            .launchIn(viewModelScope)
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCleared() {

        super.onCleared()
        GlobalScope.launch(dispatcher) {
            try {
                updateTodoUseCase(
                    id = id,
                    title = title,
                    description = description,
                    isCompleted = isComplete
                )
            } catch (_: Exception) {

            }
        }
    }

}