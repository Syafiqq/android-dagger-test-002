package com.github.syafiqq.daggertest002.controller

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.postDelayed
import com.github.syafiqq.daggertest002.R
import com.google.android.material.snackbar.Snackbar
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.HasAndroidInjector
import kotlinx.android.synthetic.main.activity_home.*
import timber.log.Timber
import javax.inject.Inject

class HomeActivity : AppCompatActivity(), DetailFragment.InteractionListener {
    @Inject
    lateinit var manager: com.github.syafiqq.daggertest002.model.service.identity.UserManager

    override fun onCreate(savedInstanceState: Bundle?) {
        inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        Handler().postDelayed(100) {
            Timber.d("User Manager ${manager == null}")
        }
    }

    fun inject(activity: Activity) {
        val application = activity.application
        if (application !is App) {
            throw RuntimeException(
                String.format(
                    "%s does not implement %s",
                    application.javaClass.canonicalName,
                    HasAndroidInjector::class.java.canonicalName
                )
            )
        }

        application.userInjection.androidInjector().inject(this)
    }

}
