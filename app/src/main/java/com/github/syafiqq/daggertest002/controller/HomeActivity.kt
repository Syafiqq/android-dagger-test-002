package com.github.syafiqq.daggertest002.controller

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.core.os.postDelayed
import com.github.syafiqq.daggertest002.R
import com.github.syafiqq.daggertest002.custom.dagger.android.AndroidInjection
import com.github.syafiqq.daggertest002.custom.dagger.android.support.DaggerAppCompatActivity
import com.github.syafiqq.daggertest002.model.api.IdentityServer
import com.github.syafiqq.daggertest002.model.concurrent.SchedulerProvider
import com.github.syafiqq.daggertest002.model.di.component.UserComponent
import com.github.syafiqq.daggertest002.model.dump.CounterContract
import com.github.syafiqq.daggertest002.model.service.identity.UserManager
import io.reactivex.Observer
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_home.*
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Named

class HomeActivity : DaggerAppCompatActivity(), DetailFragment.InteractionListener {
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
    @Inject
    @field:Named("user-scope")
    lateinit var counter2: CounterContract

    override fun desiredComponent(): Class<*> = UserComponent::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this, UserComponent::class.java)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(toolbar)

        val s: Observer<View> = PublishSubject.create<View>().apply {
            throttleFirst(350, TimeUnit.MILLISECONDS)
            subscribe {
                startActivity(Intent(this@HomeActivity, UserDetailActivity::class.java))
            }
        }

        fab.setOnClickListener(s::onNext)

        Handler().postDelayed(100) {
            Timber.d("Context : ${context == null} ${System.identityHashCode(context)}")
            Timber.d("SchedulerProvider : ${schedulers == null} ${System.identityHashCode(schedulers)}")
            Timber.d("UserManager : ${userManager == null} ${System.identityHashCode(userManager)}")
            Timber.d("IdentityServer : ${identityServer == null} ${System.identityHashCode(identityServer)}")
            Timber.d("App Counter ${counter == null} ${System.identityHashCode(counter)}")
            Timber.d("Activity Counter : ${counter1 == null} ${System.identityHashCode(counter1)}")
            Timber.d("User Counter : ${counter2 == null} ${System.identityHashCode(counter2)}")

            for (i in 1..5) {
                Timber.d("App Counter [${counter.value}]")
            }
            for (i in 1..5) {
                Timber.d("Activity Counter : [${counter1.value}]")
            }
            for (i in 1..5) {
                Timber.d("User Counter : [${counter2.value}]")
            }
        }
    }
}
