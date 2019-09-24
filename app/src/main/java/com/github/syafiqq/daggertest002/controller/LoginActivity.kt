package com.github.syafiqq.daggertest002.controller

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.postDelayed
import com.github.syafiqq.daggertest002.R
import com.github.syafiqq.daggertest002.model.dump.CounterContract
import dagger.android.AndroidInjection
import timber.log.Timber
import javax.inject.Inject

class LoginActivity : AppCompatActivity() {
    @Inject
    lateinit var counter: CounterContract

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        Timber.d("onCreate [savedInstanceState]")

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        Handler().postDelayed(100) {
            Timber.d("CounterContract : ${counter == null}")

            for(i in 1..5) {
                Timber.d("CounterContract : [${counter.value}]")
            }
        }
    }
}
