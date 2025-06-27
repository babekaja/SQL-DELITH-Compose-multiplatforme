package org.babe.sqldelight.data.dispatchers

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.CoroutineDispatcher

actual val IODispatcher: CoroutineDispatcher = Dispatchers.Default