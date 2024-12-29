package io.github.spokesugar.kmp.todo.domain

interface RemoveTodoUseCase {
    suspend operator fun invoke(id: Long)
}