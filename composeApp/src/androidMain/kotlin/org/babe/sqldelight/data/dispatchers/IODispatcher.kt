package org.babe.sqldelight.data.dispatchers

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/**
 * Sur Android, on utilise le pool I/O dédié de Coroutines.
 */
actual val IODispatcher: CoroutineDispatcher = Dispatchers.IO
