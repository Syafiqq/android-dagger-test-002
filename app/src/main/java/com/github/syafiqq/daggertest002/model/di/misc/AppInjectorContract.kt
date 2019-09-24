package com.github.syafiqq.daggertest002.model.di.misc

import com.github.syafiqq.daggertest002.controller.App
import com.github.syafiqq.daggertest002.model.di.component.AppComponent
import com.github.syafiqq.daggertest002.model.di.component.UserComponent

interface AppInjectorContract {
    val appComponent: HasComponent<AppComponent, App>
    val userComponent: HasComponent<UserComponent, App>
}