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
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [DetailFragment.InteractionListener] interface
 * to handle interaction events.
 * Use the [DetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailFragment : Fragment() {
    private var listener: InteractionListener? = null

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
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
        if (context is InteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement InteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface InteractionListener

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DetailFragment.
         */
        @JvmStatic
        fun newInstance() =
            DetailFragment()
    }
}
