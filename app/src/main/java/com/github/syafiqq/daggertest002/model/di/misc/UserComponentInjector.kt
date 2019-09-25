package com.github.syafiqq.daggertest002.model.di.misc

import com.github.syafiqq.daggertest002.model.di.component.UserComponent
import javax.inject.Inject

class UserComponentInjector @Inject constructor(private var app: HasAppComponent) :
    HasAndroidInjectorAdv<UserComponentInjector, UserComponent>() {
    override fun applicationInjector(): UserComponent {
        return app.appComponent.userComponent().create(this) as UserComponent
    }
}