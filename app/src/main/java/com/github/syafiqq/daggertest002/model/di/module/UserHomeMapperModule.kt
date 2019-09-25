package com.github.syafiqq.daggertest002.model.di.module

import com.github.syafiqq.daggertest002.controller.DetailFragment
import com.github.syafiqq.daggertest002.controller.UserFragment
import com.github.syafiqq.daggertest002.model.di.scope.FragmentScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class UserHomeMapperModule {
    @ContributesAndroidInjector
    @FragmentScope
    abstract fun contributeHomeFragment(): UserFragment

    @ContributesAndroidInjector
    @FragmentScope
    abstract fun contributeDetailFragment(): DetailFragment
}