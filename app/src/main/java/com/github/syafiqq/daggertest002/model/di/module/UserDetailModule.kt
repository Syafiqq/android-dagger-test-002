package com.github.syafiqq.daggertest002.model.di.module

import com.github.syafiqq.daggertest002.model.dump.CounterContract
import com.github.syafiqq.daggertest002.model.dump.CounterImpl
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
object UserDetailModule {
    @Provides
    @JvmStatic
    @Named("activity-scope")
    fun providedCounter(): CounterContract = CounterImpl(0)
}