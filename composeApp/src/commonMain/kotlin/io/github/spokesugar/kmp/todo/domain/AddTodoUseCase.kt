package io.github.spokesugar.kmp.todo.domain

interface AddTodoUseCase {
    suspend operator fun invoke(title: String, description: String, isCompleted: Boolean):Long
}