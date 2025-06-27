// src/jvmMain/kotlin/org/babe/sqldelight/data/dispatchers/IODispatcher.kt
package org.babe.sqldelight.data.dispatchers

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/** Sur JVM, on utilise le vrai pool IO */
actual val IODispatcher: CoroutineDispatcher = Dispatchers.IO
