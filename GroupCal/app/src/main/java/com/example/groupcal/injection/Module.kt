package com.example.groupcal.injection
import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.groupcal.data.repositories.EventRepository
import com.example.groupcal.data.repositories.GroupRepository
import com.example.groupcal.data.database.CalDatabase
import com.example.groupcal.data.database.dao.EventDAO
import com.example.groupcal.data.database.dao.GroupDAO
import com.example.groupcal.data.network.EventApi
import com.example.groupcal.data.network.GroupApi
import com.example.groupcal.util.CalAdapter
import com.example.groupcal.util.Constants
import com.example.groupcal.viewmodels.AddEventViewModel
import com.example.groupcal.viewmodels.CalendarViewModel
import com.example.groupcal.viewmodels.EventViewModel
import com.example.groupcal.viewmodels.GroupViewModel
import com.example.groupcal.viewmodels.AddGroupViewModel
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import retrofit2.Retrofit
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
            .add(CalAdapter())
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

    fun provideEventApi(retrofit: Retrofit): EventApi {
        return retrofit.create(EventApi::class.java)
    }
    factory { provideHttpLoggingInterceptor() }
    factory { provideOkHttpClient(get()) }
    factory { provideGroupApi(get()) }
    factory { provideEventApi(get()) }
    single { provideRetrofitInterface(get()) }
}

val databaseModule = module {
    fun provideDatabase(application: Application): CalDatabase {
        val db = Room.databaseBuilder(application, CalDatabase::class.java, "group_cal_database")
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

    fun provideGroupRepository(dao: GroupDAO, api: GroupApi, context: Context): GroupRepository {
        return GroupRepository(
            dao,
            api,
            context
        )
    }
    fun provideEventRepository(dao: EventDAO, api: EventApi, context: Context, groupRepo: GroupRepository): EventRepository {
        return EventRepository(
            dao,
            api,
            context,
            groupRepo
        )
    }

    factory { provideGroupRepository(get(), get(), androidContext()) }
    factory { provideEventRepository(get(), get(), androidContext(), get()) }
}