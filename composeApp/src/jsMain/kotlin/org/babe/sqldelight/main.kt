package org.babe.sqldelight

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import kotlinx.browser.document
import kotlinx.browser.window
import org.babe.sqldelight.di.sharedModule
import org.babe.sqldelight.ui.MainContent
import org.jetbrains.compose.web.renderComposable
import org.koin.core.context.startKoin

//@OptIn(ExperimentalComposeUiApi::class)
//fun main() {
//
//    ComposeViewport(document.body!!) {
//        App()
//    }
//}


fun main() {
    // 1. Démarrage de Koin (JS)
    startKoin {
        modules(sharedModule)
    }

    // 2. Rendu de l'UI Compose for Web
    window.onload = {
        renderComposable(rootElementId = "root") {
            MainContent().App()
        }
    }
}