package com.github.syafiqq.daggertest002.model.di.component

import com.github.syafiqq.daggertest002.model.di.injector.UserComponentInjector
import com.github.syafiqq.daggertest002.model.di.module.UserMapperModule
import com.github.syafiqq.daggertest002.model.di.scope.UserScope
import dagger.Subcomponent
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector

@UserScope
@Subcomponent(
    modules = [
        AndroidInjectionModule::class,
        UserMapperModule::class
    ]
)
interface UserComponent : AndroidInjector<UserComponentInjector> {
    @Subcomponent.Factory
    interface Factory : AndroidInjector.Factory<UserComponentInjector>
}