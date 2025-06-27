package org.babe.sqldelight.data.db

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver

actual fun provideDriver(): SqlDriver =
    NativeSqliteDriver(AppDatabase.Schema, "tasks.db")
