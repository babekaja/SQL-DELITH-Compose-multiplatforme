package org.babe.sqldelight

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import org.babe.sqldelight.data.repository.TaskRepository
import org.babe.sqldelight.di.sharedModule
import org.babe.sqldelight.ui.MainContent
import org.koin.core.context.GlobalContext.startKoin




fun main() = application {
    // 1. Démarrage de Koin (pour provideDriver())
    startKoin { modules(sharedModule) }

    // 2. Créer un repo et tester la DB en console
    val repo = TaskRepository()
    runBlocking {
        // vider la table
        repo.getAllTasks()
            .firstOrNull()     // obtient la liste actuelle
            ?.forEach { repo.deleteTask(it.id) }

        // insérer deux tâches de test
        repo.addTask("🧪 Tâche de test 1")
        repo.addTask("🧪 Tâche de test 2")

        // lire et afficher dans la console
        val tasks = repo.getAllTasks().first()
        println(">>> Tâches en base :")
        tasks.forEach { println(" - [${if (it.done) "x" else " "}] ${it.id}: ${it.title}") }
    }

    // 3. Démarrer l’UI
    Window(onCloseRequest = ::exitApplication, title = "MyTaskApp") {
        MainContent().App()
    }
}

//fun main() = application {
//
//    startKoin { modules(sharedModule) }
//    Window(
//        onCloseRequest = ::exitApplication,
//        title = "SqlDelight",
//    ) {
//        MainContent().App()
//    }
//}