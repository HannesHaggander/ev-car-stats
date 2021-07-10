package com.towerowl.hiltdemo.di

import com.towerowl.hiltdemo.repository.VehicleRepository
import com.towerowl.hiltdemo.repository.VehicleRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Singleton
    @Provides
    fun provideVehicleRepository(): VehicleRepository = VehicleRepositoryImpl()
}