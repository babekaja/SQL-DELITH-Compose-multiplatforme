package org.babe.sqldelight.data.db


import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.sqljs.Database
import app.cash.sqldelight.driver.sqljs.JsSqlDriver

actual  fun provideDriver(): SqlDriver {
    // Cette ligne fonctionnera une fois la dépendance mise à jour
    return JsSqlDriver(
        db = AppDatabase.Schema as Database
    )
}