package com.github.syafiqq.daggertest002.model.di.module

import com.github.syafiqq.daggertest002.controller.HomeActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class UserMapperModule {
    @ContributesAndroidInjector
    abstract fun contributeHomeActivityInjector(): HomeActivity
}