package com.luc.data.di

import android.app.Application
import androidx.room.Room
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.luc.data.ApiServiceRepositoryImpl
import com.luc.data.FirestoreRepositoryImpl
import com.luc.data.LoginRepositoryImpl
import com.luc.data.local.LocalDataSource
import com.luc.data.local.LocalDatabase
import com.luc.data.local.dao.FooDao
import com.luc.data.local.dao.UserDao
import com.luc.data.remote.api.ApiService
import com.luc.data.remote.api.ApiServiceDataSource
import com.luc.data.remote.firebase.auth.AuthenticationDataSource
import com.luc.data.remote.firebase.auth.AuthenticationDataSourceImpl
import com.luc.data.remote.firebase.firestore.FirestoreData
import com.luc.data.remote.firebase.firestore.FirestoreDataImpl
import com.luc.domain.ApiServiceRepository
import com.luc.domain.LoginRepository
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val firebaseModule = module {
    single {
        val instance = FirebaseFirestore.getInstance()

        val settings = if (isFirebaseLocal) {
            FirebaseFirestoreSettings.Builder()
                .setHost("10.0.2.2:8080")
                .setSslEnabled(false)
                .setPersistenceEnabled(false)
                .build()
        } else {
            FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(false)
                .build()
        }
        instance.firestoreSettings = settings
        instance
    }
}

val retrofitModule = module {
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(NetworkConstant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    single { provideRetrofit() }
    single { provideApiService(get()) }
}

val roomModule = module {
    fun provideDatabase(application: Application): LocalDatabase {
        return Room.databaseBuilder(application, LocalDatabase::class.java, "db")
            .fallbackToDestructiveMigration()
            .build()
    }

    fun provideUserDao(database: LocalDatabase): UserDao {
        return database.userDao()
    }

    // TODO("change the name of method for the corresponding entity name")
    fun provideFooDao(database: LocalDatabase): FooDao {
        return database.fooDao()
    }

    single { provideDatabase(androidApplication()) }
    single { provideUserDao(get()) }
    single { provideFooDao(get()) }
}

val repositoryModule = module {
    factory<FirestoreData> {
        FirestoreDataImpl(
            firestore = get(),
        )
    }
    factory { LocalDataSource(get()) }
    factory<com.luc.domain.FirestoreRepository> {
        FirestoreRepositoryImpl(
            firestoreData = get(),
            get()
        )
    }
    factory { ApiServiceDataSource(get(), get()) }

    factory<LoginRepository> { LoginRepositoryImpl(firestoreData = get(), get(), get()) }
    factory<ApiServiceRepository> { ApiServiceRepositoryImpl(get()) }
}

val authenticationModule = module {
    single {
        val auth = FirebaseAuth.getInstance()
        if (isFirebaseLocal) auth.useEmulator("10.0.2.2", 9099)
        auth
    }
    single<AuthenticationDataSource> { AuthenticationDataSourceImpl(get(), get()) }
}

val dataModule =
    listOf(repositoryModule, firebaseModule, roomModule, authenticationModule, retrofitModule)

/* Variable for local emulators testing see BaseApplication.kt*/
var isFirebaseLocal = false