package com.paws.di

import com.paws.data.repository.DataRepository
import com.paws.data.repository.DataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class BindsModule {
    @Binds
    @Singleton
    abstract fun bindDataRepository(dataRepository: DataRepository): DataSource
}