package com.github.syafiqq.daggertest002.model.di.module

import android.content.Context
import com.github.syafiqq.daggertest002.controller.App
import com.github.syafiqq.daggertest002.mode.concurrent.SchedulerProvider
import com.github.syafiqq.daggertest002.mode.concurrent.SchedulerProviderImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
abstract class AppModule {
    @Binds
    abstract fun providerSchedulerProvider(provider: SchedulerProviderImpl): SchedulerProvider

    @Module
    companion object {
        @Provides
        @Singleton
        @JvmStatic
        fun provideApplicationContext(app: App): Context {
            return app
        }
    }
}