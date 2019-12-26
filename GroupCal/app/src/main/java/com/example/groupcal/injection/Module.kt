package com.example.groupcal.injection


import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.groupcal.data.EventRepository
import com.example.groupcal.data.GroupRepository
import com.example.groupcal.data.database.CalDatabase
import com.example.groupcal.data.database.dao.EventDAO
import com.example.groupcal.data.database.dao.GroupDAO
import com.example.groupcal.data.network.GroupApi
import com.example.groupcal.util.Constants
import com.example.groupcal.viewmodels.*
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

val viewModelModule = module {
    viewModel { GroupViewModel(get()) }
    viewModel { AddEventViewModel(get()) }
    viewModel { CalendarViewModel(get()) }
    viewModel { EventViewModel(get()) }
    viewModel { AddGroupViewModel(get()) }

}

val networkModule = module {
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        // Used for Retrofit/OkHttp debugging.
        return HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }
    }

    fun provideOkHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder().apply {
            this.addInterceptor(interceptor)
        }.build()
    }

    fun provideRetrofitInterface(okHttpClient: OkHttpClient): Retrofit {
        val moshi = Moshi.Builder()
            .build()

        return Retrofit.Builder()
            .baseUrl(Constants.HEROKU_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }

    fun provideGroupApi(retrofit: Retrofit): GroupApi {
        return retrofit.create(GroupApi::class.java)
    }


    factory { provideHttpLoggingInterceptor() }
    factory { provideOkHttpClient(get()) }
    factory { provideGroupApi(get()) }
    single { provideRetrofitInterface(get()) }
}


val databaseModule = module {

    fun provideDatabase(application: Application): CalDatabase {

        val db =  Room.databaseBuilder(application, CalDatabase::class.java, "group_cal_database")
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        
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
    fun provideGroupRepository(dao: GroupDAO, api: GroupApi, context: Context): GroupRepository {
        return GroupRepository(dao, api, context)
    }

    factory { provideEventRepository(get()) }
    factory { provideGroupRepository(get(), get(), androidContext()) }
}