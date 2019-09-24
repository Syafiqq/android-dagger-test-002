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