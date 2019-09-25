package com.github.syafiqq.daggertest002.model.di.module

import com.github.syafiqq.daggertest002.model.di.component.UserComponent
import com.github.syafiqq.daggertest002.model.di.injector.HasAndroidInjectorAdv
import com.github.syafiqq.daggertest002.model.di.misc.HasAppComponent
import com.github.syafiqq.daggertest002.model.di.injector.UserComponentInjector
import dagger.Module
import dagger.Provides
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap


@Module
class InjectorModule {

    @Provides
    @IntoMap
    @ClassKey(UserComponent::class)
    fun provideThingValue(app: HasAppComponent): HasAndroidInjectorAdv<*, *> {
        return UserComponentInjector(app)
    }
}