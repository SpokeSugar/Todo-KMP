package androidx.paging.compose

internal actual fun getPagingPlaceholderKey(index: Int): Any = PagingPlaceholderKey(index = index)

private data class PagingPlaceholderKey(private val index: Int)