package com.power.button.volume.keys.remap.di

import com.power.button.volume.keys.remap.repository.KeyRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * @Author: Muhammad Hasnain Altaf
 * @Date: 23/05/2024
 */

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideKeyRepository(): KeyRepository{
        return KeyRepository()
    }
}