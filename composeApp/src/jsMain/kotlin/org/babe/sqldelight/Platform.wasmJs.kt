// jsMain/src/jsMain/kotlin/org/babe/sqldelight/Platform.kt
package org.babe.sqldelight

actual fun getPlatform(): Platform = object : Platform {
    override val name: String = "Web with Kotlin/Wasm"
}
