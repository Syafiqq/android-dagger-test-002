package com.github.syafiqq.daggertest002.model.di.module

import com.github.syafiqq.daggertest002.model.di.scope.UserScope
import com.github.syafiqq.daggertest002.model.dump.CounterContract
import com.github.syafiqq.daggertest002.model.dump.CounterImpl
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module(includes = [StaticUserModule::class])
abstract class UserModule

@Module
object StaticUserModule {
    @Provides
    @UserScope
    @JvmStatic
    @Named("user-scope")
    fun providedCounter(): CounterContract = CounterImpl(0)
}