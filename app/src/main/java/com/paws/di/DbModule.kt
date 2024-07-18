package com.paws.di

import android.content.Context
import androidx.room.Room
import com.paws.data.local.persistence.DogBreedsDatabase
import com.paws.utils.Constants.DB_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DbModule {

    @Singleton
    @Provides
    fun provideYourDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        DogBreedsDatabase::class.java,
        DB_NAME
    ).fallbackToDestructiveMigration()
        .allowMainThreadQueries().build()

    @Singleton
    @Provides
    fun provideYourDao(db: DogBreedsDatabase) =
        db.dogBreedsDao()
}