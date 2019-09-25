package com.github.syafiqq.daggertest002.model.di.module

import com.github.syafiqq.daggertest002.controller.HomeActivity
import com.github.syafiqq.daggertest002.controller.UserDetailActivity
import com.github.syafiqq.daggertest002.model.di.scope.ActivityScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class UserMapperModule {
    @ContributesAndroidInjector
    @ActivityScope
    abstract fun contributeHomeActivityInjector(): HomeActivity

    @ContributesAndroidInjector
    @ActivityScope
    abstract fun contributeDetailActivityInjector(): UserDetailActivity
}