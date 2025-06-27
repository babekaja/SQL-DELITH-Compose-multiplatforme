// src/commonMain/kotlin/org/babe/sqldelight/data/dispatchers/IODispatcher.kt
package org.babe.sqldelight.data.dispatchers

import kotlinx.coroutines.CoroutineDispatcher

/** Dispatcher pour les opérations I/O ; actualisé par plateforme */
expect val IODispatcher: CoroutineDispatcher
