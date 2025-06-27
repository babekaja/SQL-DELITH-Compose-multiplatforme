package org.babe.sqldelight.di

import org.babe.sqldelight.data.repository.TaskRepository
import org.koin.dsl.module


val sharedModule = module {
    single { TaskRepository() }
}
