package org.babe.sqldelight

import androidx.compose.ui.window.ComposeUIViewController
import org.babe.sqldelight.di.sharedModule
import org.babe.sqldelight.ui.MainContent
import org.koin.core.context.startKoin

fun MainViewController() = ComposeUIViewController {
    startKoin { modules(sharedModule) }
    MainContent().App()
}
