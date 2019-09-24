package com.github.syafiqq.daggertest002.model.di.component

import com.github.syafiqq.daggertest002.controller.HomeActivity
import com.github.syafiqq.daggertest002.model.di.module.UserMapperModule
import com.github.syafiqq.daggertest002.model.di.scope.UserScope
import dagger.Component

@UserScope
@Component(
    modules = [UserMapperModule::class],
    dependencies = [AppComponent::class]
)
interface UserComponent {
    fun inject(home: HomeActivity)
}