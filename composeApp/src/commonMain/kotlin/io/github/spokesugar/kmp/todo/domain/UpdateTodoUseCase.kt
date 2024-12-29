package io.github.spokesugar.kmp.todo.domain

interface UpdateTodoUseCase {
    suspend operator fun invoke(id:Long, title:String? = null, description: String? = null, isCompleted:Boolean? = null)
}