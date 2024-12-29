package io.github.spokesugar.kmp.todo.domain

interface ToggleTodoUseCase {
    suspend operator fun invoke(id: Long,isCompleted: Boolean)
}