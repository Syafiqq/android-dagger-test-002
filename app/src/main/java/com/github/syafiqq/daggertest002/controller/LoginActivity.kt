package com.github.syafiqq.daggertest002.controller

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.postDelayed
import com.github.syafiqq.daggertest002.R
import com.github.syafiqq.ext.dagger.android.AndroidInjection
import com.github.syafiqq.daggertest002.model.api.IdentityServer
import com.github.syafiqq.daggertest002.model.concurrent.SchedulerProvider
import com.github.syafiqq.daggertest002.model.dump.CounterContract
import com.github.syafiqq.daggertest002.model.entity.UserEntity
import com.github.syafiqq.daggertest002.model.service.identity.UserManager
import io.reactivex.Observer
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_login.*
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Named

class LoginActivity : AppCompatActivity() {
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

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this, null)
        Timber.d("onCreate [savedInstanceState]")

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

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

        val s: Observer<View> = PublishSubject.create<View>().apply {
            throttleFirst(350, TimeUnit.MILLISECONDS)
            subscribe(::onLogin)
        }

        button.setOnClickListener(s::onNext)
    }

    private fun onLogin(view: View) {
        fun loginSuccess(user: UserEntity) {
            Toast.makeText(context, "Welcome ${user.name}", Toast.LENGTH_LONG).show()
            startActivity(Intent(this, HomeActivity::class.java)).also {
                finish()
            }
        }
        fun loginFailed(error: Throwable) {
            Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
        }

        userManager
            .login(email.editText?.text.toString(), password.editText?.text.toString())
            .subscribeOn(schedulers.computation())
            .observeOn(schedulers.ui())
            .subscribe(::loginSuccess, ::loginFailed)
    }
}
