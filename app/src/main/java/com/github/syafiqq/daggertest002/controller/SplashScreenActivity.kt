package com.github.syafiqq.daggertest002.controller

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.postDelayed
import com.github.syafiqq.daggertest002.R
import com.github.syafiqq.ext.dagger.android.AndroidInjection
import com.github.syafiqq.daggertest002.model.api.IdentityServer
import com.github.syafiqq.daggertest002.model.concurrent.SchedulerProvider
import com.github.syafiqq.daggertest002.model.dump.CounterContract
import com.github.syafiqq.daggertest002.model.service.identity.UserManager
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_splash_screen.*
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Named

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class SplashScreenActivity : AppCompatActivity() {
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
    @Inject
    @field:Named("activity-scope")
    lateinit var counter1: CounterContract

    private val mHideHandler = Handler()
    private val mHidePart2Runnable = Runnable {
        // Delayed removal of status and navigation bar

        // Note that some of these constants are new as of API 16 (Jelly Bean)
        // and API 19 (KitKat). It is safe to use them, as they are inlined
        // at compile-time and do nothing on earlier devices.
        fullscreen_content.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LOW_PROFILE or
                    View.SYSTEM_UI_FLAG_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
    }
    private var mVisible: Boolean = false
    private val mHideRunnable = Runnable(::hide)

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this, null)
        Timber.d("onCreate [saveInstanceState]")
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash_screen)

        mVisible = true
        Observable.defer(::dummyWaiting)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                startActivity(Intent(this, LoginActivity::class.java)).also {
                    finish()
                }
            }
        Handler().postDelayed(100) {
            Timber.d("Context : ${context == null} ${System.identityHashCode(context)}")
            Timber.d("SchedulerProvider : ${schedulers == null} ${System.identityHashCode(schedulers)}")
            Timber.d("UserManager : ${userManager == null} ${System.identityHashCode(userManager)}")
            Timber.d("IdentityServer : ${identityServer == null} ${System.identityHashCode(identityServer)}")
            Timber.d("App Counter ${counter == null} ${System.identityHashCode(counter)}")
            Timber.d("Activity Counter : ${counter1 == null} ${System.identityHashCode(counter1)}")

            for (i in 1..5) {
                Timber.d("App Counter [${counter.value}]")
            }
            for (i in 1..5) {
                Timber.d("Activity Counter : [${counter1.value}]")
            }
        }
    }

    private fun dummyWaiting(): ObservableSource<Int> {
        Timber.d("dummyWaiting []")
        SystemClock.sleep(500)
        return Observable.just(1)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        Timber.d("onCreate [saveInstanceState]")
        super.onPostCreate(savedInstanceState)

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100)
    }

    private fun hide() {
        Timber.d("hide []")
        // Hide UI first
        supportActionBar?.hide()
        mVisible = false

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY.toLong())
    }

    /**
     * Schedules a call to hide() in [delayMillis], canceling any
     * previously scheduled calls.
     */
    private fun delayedHide(delayMillis: Int) {
        Timber.d("delayedHide [delayMillis:$delayMillis]")

        mHideHandler.removeCallbacks(mHideRunnable)
        mHideHandler.postDelayed(mHideRunnable, delayMillis.toLong())
    }

    companion object {
        /**
         * Some older devices needs a small delay between UI widget updates
         * and a change of the status and navigation bar.
         */
        private const val UI_ANIMATION_DELAY = 300
    }
}
