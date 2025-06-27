package org.babe.sqldelight.data.model

data class Task(
    val id: Long,
    val title: String,
    val done: Boolean
)