package org.babe.sqldelight.ui


import androidx.compose.runtime.*
import kotlinx.coroutines.launch
import org.babe.sqldelight.data.repository.TaskRepository

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class MainContent : KoinComponent {
    private val repo by inject<TaskRepository>()

    @Composable
    fun App() {
        val tasks by repo.getAllTasks().collectAsState(initial = emptyList())
        var showAdd by remember { mutableStateOf(false) }
        val scope = rememberCoroutineScope()

        if (showAdd) {
            AddTaskScreen(
                onSave = { title ->
                    scope.launch { repo.addTask(title) }
                    showAdd = false
                },
                onCancel = { showAdd = false }
            )
        } else {
            TaskListScreen(
                tasks = tasks,
                onToggle = { task ->
                    scope.launch { repo.toggleDone(task) }
                },
                onDelete = { id ->
                    scope.launch { repo.deleteTask(id) }
                },
                onAdd = { showAdd = true }
            )
        }
    }
}
