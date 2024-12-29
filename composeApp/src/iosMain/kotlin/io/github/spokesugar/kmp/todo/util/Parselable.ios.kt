package io.github.spokesugar.kmp.todo.util

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.SOURCE)
actual annotation class IgnoredOnParcel actual constructor()

actual interface Parcelable