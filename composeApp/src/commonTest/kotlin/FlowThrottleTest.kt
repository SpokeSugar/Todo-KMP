import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

fun <T> Flow<T>.throttleLatest(delayMillis: Long): Flow<T> = this
    .conflate()
    .transform {
        emit(it)
        delay(delayMillis)
    }

fun <T> Flow<T>.dropIfBusy(): Flow<T> = channelFlow {
    collect { trySend(it) }
}.buffer(0)

class FlowThrottleTest {

    @Test
    fun test_throttleLatest() = runTest {
        val flow = flow {
            for (i in 1..45) {
                delay(100)
                emit(i)
            }
        }
        val result = flow.throttleLatest(1000).toList()
        assertEquals(listOf(1, 10, 20, 30, 40, 45), result)
    }
}