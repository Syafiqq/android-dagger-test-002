package com.github.syafiqq.daggertest002.controller

import android.content.Context
import android.os.Handler
import android.util.Log
import androidx.core.os.postDelayed
import com.github.syafiqq.daggertest002.BuildConfig
import com.github.syafiqq.daggertest002.model.concurrent.SchedulerProvider
import com.github.syafiqq.daggertest002.model.di.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import timber.log.Timber
import javax.inject.Inject

class App : DaggerApplication() {
    @Inject
    lateinit var context:Context
    @Inject
    lateinit var schedulers: SchedulerProvider

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.factory().create(this)
    }

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(CrashReportingTree())
        }

        Handler().postDelayed(2000) {
            Timber.d("Context : ${context == null}")
            Timber.d("SchedulerProvider : ${schedulers == null}")
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