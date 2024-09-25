package me.kofesst.lovemood.async

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * Статусы снапшота асинхронного значения.
 *
 * [Idle] - изначальный статус снапшота, над которым не было произведено ни одного действия.
 *
 * [Loading] - статус снапшота при загрузке данных.
 *
 * [Failed] - статус снапшота при неудачной загрузке данных.
 *
 * [Loaded] - статус снапшота при успешной загрузке данных.
 */
enum class SnapshotStatus {
    Idle,
    Loading,
    Failed,
    Loaded
}

/**
 * Снапшот асинхронного значения.
 *
 * [value] - загруженное значение. Является null, если:
 *
 * - значение не было ни разу успешно загружено ([hasLoadedBefore]` = false`);
 *
 * - значение было загружено, но было равно `null`.
 *
 * [error] - ошибка при последней загрузке данных.
 *
 * [status] - статус снапшота.
 *
 * [hasLoadedBefore] - был ли снапшот успешно загруженным ранее. При значении
 * `true` можно использовать раннее загруженное значение [value] - оно будет
 * равняться последнему загруженному значению.
 */
data class SnapshotValue<T : Any>(
    val value: T? = null,
    val error: Exception? = null,
    val status: SnapshotStatus = SnapshotStatus.Idle,
    val hasLoadedBefore: Boolean = false
)

/**
 * Загружает снапшот, используя [MutableSharedFlow].
 *
 * [loadingBlock] - блок загрузки данных.
 */
suspend fun <T : Any> MutableStateFlow<SnapshotValue<T>>.loadSnapshot(
    loadingBlock: suspend () -> T?
) {
    val initialValue = value
    emit(
        initialValue.copy(
            status = SnapshotStatus.Loading
        )
    )
    try {
        val loadedValue = loadingBlock()
        emit(
            initialValue.copy(
                status = SnapshotStatus.Loaded,
                value = loadedValue,
                error = null,
                hasLoadedBefore = true
            )
        )
    } catch (error: Exception) {
        emit(
            initialValue.copy(
                status = SnapshotStatus.Failed,
                value = null,
                error = error,
                hasLoadedBefore = true
            )
        )
    }
}