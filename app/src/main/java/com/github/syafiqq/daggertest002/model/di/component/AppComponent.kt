package com.github.syafiqq.daggertest002.model.di.component

import com.github.syafiqq.daggertest002.controller.App
import com.github.syafiqq.daggertest002.model.di.module.ActivityMapperModule
import com.github.syafiqq.daggertest002.model.di.module.AppModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class,
        ActivityMapperModule::class
    ]
)
interface AppComponent : AndroidInjector<App> {
    @Component.Factory
    interface Factory : AndroidInjector.Factory<App>
}