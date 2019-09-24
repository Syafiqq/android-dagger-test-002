package com.github.syafiqq.daggertest002.model.di.module

import com.github.syafiqq.daggertest002.controller.LoginActivity
import com.github.syafiqq.daggertest002.controller.SplashScreenActivity
import com.github.syafiqq.daggertest002.model.di.scope.ActivityScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityMapperModule {
    @ContributesAndroidInjector
    @ActivityScope
    abstract fun contributeDumpAndroidInjector(): SplashScreenActivity

    @ContributesAndroidInjector
    @ActivityScope
    abstract fun contributeDetailAndroidInjector(): LoginActivity
}