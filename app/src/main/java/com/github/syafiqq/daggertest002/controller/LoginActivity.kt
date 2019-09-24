package com.github.syafiqq.daggertest002.controller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.syafiqq.daggertest002.R
import dagger.android.AndroidInjection
import timber.log.Timber

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        Timber.d("onCreate [savedInstanceState]")

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }
}
