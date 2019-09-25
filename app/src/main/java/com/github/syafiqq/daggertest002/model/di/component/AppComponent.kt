package com.github.syafiqq.daggertest002.model.di.component

import com.github.syafiqq.daggertest002.controller.App
import com.github.syafiqq.daggertest002.model.di.misc.HasAndroidInjectorAdv
import com.github.syafiqq.daggertest002.model.di.module.ActivityMapperModule
import com.github.syafiqq.daggertest002.model.di.module.AppModule
import com.github.syafiqq.daggertest002.model.di.module.InjectorModule
import com.github.syafiqq.daggertest002.model.service.identity.UserManager
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class,
        ActivityMapperModule::class,
        InjectorModule::class
    ]
)
interface AppComponent : AndroidInjector<App> {
    @Component.Factory
    interface Factory : AndroidInjector.Factory<App>

    fun userComponent(): UserComponent.Factory
    fun userManager(): UserManager
}