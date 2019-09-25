package com.github.syafiqq.daggertest002.model.di.component

import com.github.syafiqq.daggertest002.controller.App
import com.github.syafiqq.daggertest002.model.di.module.UserMapperModule
import com.github.syafiqq.daggertest002.model.di.module.UserModule
import com.github.syafiqq.daggertest002.model.di.scope.UserScope
import dagger.Subcomponent
import dagger.android.AndroidInjector

@UserScope
@Subcomponent(
    modules = [
        UserModule::class,
        UserMapperModule::class
    ]
)
interface UserComponent : AndroidInjector<App> {
    @Subcomponent.Factory
    interface Factory : AndroidInjector.Factory<App>
}