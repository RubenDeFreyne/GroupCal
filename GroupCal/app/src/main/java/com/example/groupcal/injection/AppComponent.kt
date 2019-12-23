package com.example.groupcal.injection

import org.koin.dsl.module.Module

val appComponent: List<Module> = listOf(viewModelModule, databaseModule, repositoryModule)