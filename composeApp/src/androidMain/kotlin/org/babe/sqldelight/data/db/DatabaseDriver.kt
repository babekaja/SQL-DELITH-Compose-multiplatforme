package org.babe.sqldelight.data.db


import android.content.Context
import app.cash.sqldelight.db.SqlDriver

import app.cash.sqldelight.driver.android.AndroidSqliteDriver

lateinit var appContext: Context

actual fun provideDriver(): SqlDriver =
    AndroidSqliteDriver(AppDatabase.Schema, appContext, "tasks.db")
