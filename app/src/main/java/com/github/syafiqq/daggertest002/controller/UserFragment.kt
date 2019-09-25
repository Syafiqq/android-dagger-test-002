package com.github.syafiqq.daggertest002.controller

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.postDelayed
import androidx.fragment.app.Fragment
import com.github.syafiqq.daggertest002.R
import com.github.syafiqq.daggertest002.model.api.IdentityServer
import com.github.syafiqq.daggertest002.model.concurrent.SchedulerProvider
import com.github.syafiqq.daggertest002.model.dump.CounterContract
import com.github.syafiqq.daggertest002.model.service.identity.UserManager
import dagger.android.support.AndroidSupportInjection
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Named

/**
 * A placeholder fragment containing a simple view.
 */
class UserFragment : Fragment() {

    @Inject
    lateinit var context1: Context
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
    @Inject
    @field:Named("fragment-scope")
    lateinit var counter3: CounterContract

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Handler().postDelayed(100) {
            Timber.d("Context : ${context1 == null} ${System.identityHashCode(context1)}")
            Timber.d("SchedulerProvider : ${schedulers == null} ${System.identityHashCode(schedulers)}")
            Timber.d("UserManager : ${userManager == null} ${System.identityHashCode(userManager)}")
            Timber.d("IdentityServer : ${identityServer == null} ${System.identityHashCode(identityServer)}")
            Timber.d("App Counter ${counter == null} ${System.identityHashCode(counter)}")
            Timber.d("Activity Counter : ${counter1 == null} ${System.identityHashCode(counter1)}")
            Timber.d("User Counter : ${counter2 == null} ${System.identityHashCode(counter2)}")
            Timber.d("Fragment Counter : ${counter3 == null} ${System.identityHashCode(counter3)}")

            for (i in 1..5) {
                Timber.d("App Counter [${counter.value}]")
            }
            for (i in 1..5) {
                Timber.d("Activity Counter : [${counter1.value}]")
            }
            for (i in 1..5) {
                Timber.d("User Counter : [${counter2.value}]")
            }
            for (i in 1..5) {
                Timber.d("Fragment Counter : [${counter3.value}]")
            }
        }
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }
}
