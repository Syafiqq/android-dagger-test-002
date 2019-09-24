package com.github.syafiqq.daggertest002.controller

import android.os.Bundle
import android.os.Handler
import android.os.UserManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.postDelayed
import com.github.syafiqq.daggertest002.R
import com.github.syafiqq.daggertest002.model.dump.CounterContract
import dagger.android.AndroidInjection
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Named

class LoginActivity : AppCompatActivity() {
    @Inject
    @field:Named("app-scope")
    lateinit var counter: CounterContract
    @Inject
    @field:Named("activity-scope")
    lateinit var counter1: CounterContract

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        Timber.d("onCreate [savedInstanceState]")

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        Handler().postDelayed(100) {
            Timber.d("App Counter ${counter == null}")
            Timber.d("Activity Counter : ${counter1 == null}")

            for (i in 1..5) {
                Timber.d("App Counter [${counter.value}]")
            }
            for (i in 1..5) {
                Timber.d("Activity Counter : [${counter1.value}]")
            }
        }
    }
}
