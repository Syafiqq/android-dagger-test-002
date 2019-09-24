package com.github.syafiqq.daggertest002.controller

import android.content.Context
import android.os.Handler
import android.util.Log
import androidx.core.os.postDelayed
import com.github.syafiqq.daggertest002.BuildConfig
import com.github.syafiqq.daggertest002.model.concurrent.SchedulerProvider
import com.github.syafiqq.daggertest002.model.di.component.AppComponent
import com.github.syafiqq.daggertest002.model.di.component.DaggerAppComponent
import com.github.syafiqq.daggertest002.model.di.component.UserComponent
import com.github.syafiqq.daggertest002.model.di.misc.HasAppComponent
import com.github.syafiqq.daggertest002.model.dump.CounterContract
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.DaggerApplication
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Named

class Dafuq : HasAndroidInjector {
    var app: App? = null
    private var component: UserComponent? = null
    @Inject
    @Volatile
    var androidInjector: DispatchingAndroidInjector<Any>? = null

    private fun injectIfNecessary() {
        if (androidInjector == null) {
            synchronized(this) {
                if (androidInjector == null) {
                    val applicationInjector = applicationInjector()
                    applicationInjector.inject(this)
                    checkNotNull(androidInjector) { "The AndroidInjector returned from applicationInjector() did not inject the " + "DaggerApplication" }
                }
            }
        }
    }

    fun applicationInjector(): AndroidInjector<Dafuq> {
/*        if (component == null)
            component = app.appComponent.userComponent().create(this) as UserComponent
        return component!!*/
        return component!!
    }

    override fun androidInjector(): AndroidInjector<Any> {
        injectIfNecessary()

        return androidInjector!!
    }
}

class App : DaggerApplication(), HasAppComponent {
    override lateinit var appComponent: AppComponent
    @Inject
    lateinit var context: Context
    @Inject
    lateinit var schedulers: SchedulerProvider
    @Inject
    @field:Named("app-scope")
    lateinit var counter: CounterContract

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        appComponent = DaggerAppComponent.factory().create(this) as AppComponent
        return appComponent
    }

    var userInjection = Dafuq()?.apply {
        app = this@App
    }

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(CrashReportingTree())
        }

        Handler().postDelayed(100) {
            Timber.d("Context : ${context == null}")
            Timber.d("SchedulerProvider : ${schedulers == null}")
            Timber.d("App Counter ${counter == null}")

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