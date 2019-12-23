package com.example.groupcal.injection


import android.app.Application
import androidx.room.Room
import com.example.groupcal.data.EventRepository
import com.example.groupcal.data.GroupRepository
import com.example.groupcal.database.CalDatabase
import com.example.groupcal.database.dao.EventDAO
import com.example.groupcal.database.dao.GroupDAO
import com.example.groupcal.viewmodels.*
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.experimental.builder.viewModel
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import org.koin.android.ext.koin.androidContext

val viewModelModule = module {
    viewModel { GroupViewModel(get()) }
    viewModel { AddEventViewModel(get()) }
    viewModel { CalendarViewModel(get()) }
    viewModel { EventViewModel(get()) }
    viewModel { AddGroupViewModel(get()) }

}



val databaseModule = module {

    fun provideDatabase(application: Application): CalDatabase {

        val db =  Room.databaseBuilder(application, CalDatabase::class.java, "group_cal_database")
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()

        db.clearAllTables()
        return db

    }

    fun provideEventDao(database: CalDatabase): EventDAO {
        return database.EventDAO()
    }

    fun provideGroupDao(database: CalDatabase): GroupDAO {
        return database.GroupDAO()
    }

    single { provideDatabase(androidApplication()) }
    single { provideEventDao(get()) }
    single { provideGroupDao(get()) }

}

val repositoryModule = module {
    fun provideEventRepository(dao: EventDAO): EventRepository {
        return EventRepository(dao)
    }
    fun provideGroupRepository(dao: GroupDAO): GroupRepository {
        return GroupRepository(dao)
    }

    factory { provideEventRepository(get()) }
    factory { provideGroupRepository(get()) }
}