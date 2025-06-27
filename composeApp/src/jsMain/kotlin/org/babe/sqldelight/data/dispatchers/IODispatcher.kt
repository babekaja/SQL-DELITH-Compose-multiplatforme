// src/jsMain/kotlin/org/babe/sqldelight/data/dispatchers/IODispatcher.kt
package org.babe.sqldelight.data.dispatchers

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/** Sur JS, on alias vers Default (pas de pool dédié) */
actual val IODispatcher: CoroutineDispatcher = Dispatchers.Default
