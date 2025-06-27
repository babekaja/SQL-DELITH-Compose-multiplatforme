package org.babe.sqldelight.data.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import org.babe.sqldelight.data.db.AppDatabase
import org.babe.sqldelight.data.db.provideDriver
import org.babe.sqldelight.data.dispatchers.IODispatcher
import org.babe.sqldelight.data.model.Task

class TaskRepository(
    private val dispatcher: CoroutineDispatcher = IODispatcher
) {
    private val db      = AppDatabase(provideDriver())
    private val queries = db.taskQueries

    fun getAllTasks(): Flow<List<Task>> =
        queries
            .selectAll()
            .asFlow()
            .mapToList(dispatcher)
            .map { rows ->
                rows.map { row ->
                    Task(id = row.id, title = row.title, done = (row.done == 1L))
                }
            }

    suspend fun addTask(title: String) =
        withContext(dispatcher) {
            queries.insertTask(title, 0L)
        }

    suspend fun deleteTask(id: Long) =
        withContext(dispatcher) {
            queries.deleteTask(id)
        }

    suspend fun toggleDone(task: Task) =
        withContext(dispatcher) {
            queries.updateStatus(if (task.done) 0L else 1L, task.id)
        }
}