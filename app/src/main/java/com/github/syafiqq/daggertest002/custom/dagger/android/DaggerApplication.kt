package com.github.syafiqq.daggertest002.custom.dagger.android

import android.app.Application
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.internal.Beta
import javax.inject.Inject

/**
 * An [Application] that injects its members and can be used to inject objects that the
 * Android framework instantiates, such as Activitys, Fragments, or Services. Injection is performed
 * in [.onCreate] or the first call to [AndroidInjection.inject],
 * whichever happens first.
 */
@Beta
abstract class DaggerApplication : Application(), HasAndroidInjector {
    private class DefClass

    private var holder: MutableMap<Class<*>, Any> = mutableMapOf()
    private var injectors: MutableMap<Class<*>, DispatchingAndroidInjector<Any>> = mutableMapOf()

    @Inject
    @Volatile
    @JvmField
    internal var androidInjector: DispatchingAndroidInjector<Any>? = null

    override fun onCreate() {
        super.onCreate()
        injectIfNecessary(DefClass::class.java)
    }

    /**
     * Implementations should return an [AndroidInjector] for the concrete [ ]. Typically, that injector is a [dagger.Component].
     */
    protected abstract fun applicationInjector(
        cls: Class<*>,
        holder: MutableMap<Class<*>, Any>
    ): AndroidInjector<Any>

    /**
     * Lazily injects the [DaggerApplication]'s members. Injection cannot be performed in [ ][Application.onCreate] since [android.content.ContentProvider]s' [ ][android.content.ContentProvider.onCreate] method will be called first and might
     * need injected members on the application. Injection is not performed in the constructor, as
     * that may result in members-injection methods being called before the constructor has completed,
     * allowing for a partially-constructed instance to escape.
     */
    private fun injectIfNecessary(cls: Class<*>) {
        if (injectors[cls] == null) {
            synchronized(this) {
                if (injectors[cls] == null) {
                    if (!holder.containsKey(cls))
                        holder[cls] = applicationInjector(cls, holder)
                    val applicationInjector = holder[cls] as AndroidInjector<DaggerApplication>
                    applicationInjector.inject(this)
                    checkNotNull(androidInjector) { "The AndroidInjector returned from applicationInjector() did not inject the " + "DaggerApplication" }
                    injectors[cls] = androidInjector!!
                    return
                }
            }
        }


        synchronized(this) {
            androidInjector = injectors[cls]
        }
    }

    @Synchronized
    override fun androidInjector(cls: Class<*>): AndroidInjector<Any>? {
        // injectIfNecessary should already be called unless we are about to inject a ContentProvider,
        // which can happen before Application.onCreate()
        injectIfNecessary(cls)

        return androidInjector
    }

    override fun clear(cls: Class<*>) {
        holder.remove(cls)
        injectors.remove(cls)
    }

    override fun exists(cls: Class<*>): Boolean {
        return injectors.containsKey(cls)
    }
}