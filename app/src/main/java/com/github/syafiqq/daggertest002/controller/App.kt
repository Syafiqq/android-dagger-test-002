package com.github.syafiqq.daggertest002.controller

import android.content.Context
import android.os.Handler
import android.util.Log
import androidx.core.os.postDelayed
import com.github.syafiqq.daggertest002.BuildConfig
import com.github.syafiqq.ext.dagger.android.DaggerApplication
import com.github.syafiqq.daggertest002.model.api.IdentityServer
import com.github.syafiqq.daggertest002.model.concurrent.SchedulerProvider
import com.github.syafiqq.daggertest002.model.di.component.AppComponent
import com.github.syafiqq.daggertest002.model.di.component.DaggerAppComponent
import com.github.syafiqq.daggertest002.model.di.component.UserComponent
import com.github.syafiqq.daggertest002.model.dump.CounterContract
import com.github.syafiqq.daggertest002.model.service.identity.UserManager
import dagger.android.AndroidInjector
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Named


class App : DaggerApplication() {
    @Inject
    lateinit var context: Context
    @Inject
    lateinit var schedulers: SchedulerProvider
    @Inject
    lateinit var userManager: UserManager
    @Inject
    lateinit var identityServer: IdentityServer
    @Inject
    @field:Named("app-scope")
    lateinit var counter: CounterContract

    override fun applicationInjector(
        cls: Class<*>,
        holder: MutableMap<Class<*>, Any>
    ): AndroidInjector<*> {
        return when (cls) {
            UserComponent::class.java -> {
                val appComponent = holder[AppComponent::class.java] as AppComponent
                appComponent.userComponent().create(this)
            }
            else -> {
                DaggerAppComponent.factory().create(this)
            }
        }

    }

    override fun rootComponent(): Class<*> = AppComponent::class.java

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(CrashReportingTree())
        }

        Handler().postDelayed(100) {
            Timber.d("Context : ${context == null} ${System.identityHashCode(context)}")
            Timber.d("SchedulerProvider : ${schedulers == null} ${System.identityHashCode(schedulers)}")
            Timber.d("UserManager : ${userManager == null} ${System.identityHashCode(userManager)}")
            Timber.d("IdentityServer : ${identityServer == null} ${System.identityHashCode(identityServer)}")
            Timber.d("App Counter ${counter == null} ${System.identityHashCode(counter)}")

            for (i in 1..5) {
                Timber.d("App Counter [${counter.value}]")
            }
        }
    }

    private class CrashReportingTree : Timber.Tree() {
        override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
            if (priority == Log.VERBOSE || priority == Log.DEBUG) {
                return
            }
            super.log(priority, tag, message, t)
        }
    }
}