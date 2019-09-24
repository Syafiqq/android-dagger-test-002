package com.github.syafiqq.daggertest002.model.di.misc

import com.github.syafiqq.daggertest002.controller.App
import com.github.syafiqq.daggertest002.model.di.component.AppComponent
import com.github.syafiqq.daggertest002.model.di.component.UserComponent
import dagger.android.AndroidInjector
import javax.inject.Inject

class AppInjectorImpl @Inject constructor(app: App) : AppInjectorContract {
    override val appComponent: HasComponent<AppComponent, App> =
        object : HasComponent<AppComponent, App> {
            var component: AppComponent? = null
            override fun aquire(): AppComponent {
                if (component == null)
                    component = app.appComponent
                return component!!
            }

            override fun exist(): Boolean = component != null

            override fun clear() {
                component = null
            }

            override fun androidInjector(): AndroidInjector<Any> {
                return aquire() as AndroidInjector<Any>
            }
        }
    override val userComponent: HasComponent<UserComponent, App> =
        object : HasComponent<UserComponent, App> {
            var component: UserComponent? = null
            override fun aquire(): UserComponent {
                if (component == null)
                    component = appComponent.aquire().userComponent().create(app) as UserComponent
                return component!!
            }

            override fun exist(): Boolean = component != null

            override fun clear() {
                component = null
            }

            override fun androidInjector(): AndroidInjector<Any> {
                return aquire() as AndroidInjector<Any>
            }
        }

}