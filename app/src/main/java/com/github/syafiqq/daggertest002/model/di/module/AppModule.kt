package com.github.syafiqq.daggertest002.model.di.module

import android.content.Context
import com.github.syafiqq.daggertest002.controller.App
import com.github.syafiqq.daggertest002.model.api.IdentityServer
import com.github.syafiqq.daggertest002.model.api.IdentityServerImpl
import com.github.syafiqq.daggertest002.model.concurrent.SchedulerProvider
import com.github.syafiqq.daggertest002.model.concurrent.SchedulerProviderImpl
import com.github.syafiqq.daggertest002.model.service.identity.UserManager
import com.github.syafiqq.daggertest002.model.service.identity.UserManagerImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [StaticAppModule::class])
abstract class AppModule {
    @Binds
    abstract fun providerSchedulerProvider(provider: SchedulerProviderImpl): SchedulerProvider
}

@Module
object StaticAppModule {
    @Provides
    @Singleton
    @JvmStatic
    fun provideApplicationContext(app: App): Context = app

    @Provides
    @Singleton
    @JvmStatic
    fun provideIdentityServer(): IdentityServer = IdentityServerImpl()

    @Provides
    @Singleton
    @JvmStatic
    fun provideUserManager(server: IdentityServer): UserManager = UserManagerImpl(server)
}