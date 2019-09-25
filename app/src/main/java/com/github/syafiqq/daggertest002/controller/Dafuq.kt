package com.github.syafiqq.daggertest002.controller

import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class Dafuq : HasAndroidInjector {
    lateinit var app: App
    @Inject
    @Volatile
    @JvmField
    var androidInjector: DispatchingAndroidInjector<Any>? = null

    /**
     * Lazily injects the [DaggerApplication]'s members. Injection cannot be performed in [ ][Application.onCreate] since [android.content.ContentProvider]s' [ ][android.content.ContentProvider.onCreate] method will be called first and might
     * need injected members on the application. Injection is not performed in the constructor, as
     * that may result in members-injection methods being called before the constructor has completed,
     * allowing for a partially-constructed instance to escape.
     */
    private fun injectIfNecessary() {
        if (androidInjector == null) {
            synchronized(this) {
                if (androidInjector == null) {
                    val applicationInjector =
                        applicationInjector() as AndroidInjector<Dafuq>
                    applicationInjector.inject(this)
                    checkNotNull(androidInjector) { "The AndroidInjector returned from applicationInjector() did not inject the " + "DaggerApplication" }
                }
            }
        }
    }

    fun applicationInjector(): AndroidInjector<Dafuq> {
        return app.appComponent.userComponent().create(this)
    }

    override fun androidInjector(): AndroidInjector<Any>? {
        // injectIfNecessary should already be called unless we are about to inject a ContentProvider,
        // which can happen before Application.onCreate()
        injectIfNecessary()

        return androidInjector
    }
}