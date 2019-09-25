package com.github.syafiqq.daggertest002.controller

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.core.os.postDelayed
import com.github.syafiqq.daggertest002.R
import com.github.syafiqq.daggertest002.custom.dagger.android.AndroidInjection
import com.github.syafiqq.daggertest002.custom.dagger.android.support.DaggerAppCompatActivity
import com.github.syafiqq.daggertest002.model.di.component.UserComponent
import io.reactivex.Observer
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_home.*
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class HomeActivity : DaggerAppCompatActivity(), DetailFragment.InteractionListener {
    @Inject
    lateinit var manager: com.github.syafiqq.daggertest002.model.service.identity.UserManager

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
            Timber.d("User Manager ${manager == null}")
        }
    }
}
