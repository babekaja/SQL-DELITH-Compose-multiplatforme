package org.babe.sqldelight.data.db

import app.cash.sqldelight.db.SqlDriver

expect fun provideDriver(): SqlDriver
