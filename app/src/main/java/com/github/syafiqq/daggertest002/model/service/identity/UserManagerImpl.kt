package com.github.syafiqq.daggertest002.model.service.identity

import com.github.syafiqq.daggertest002.model.api.IdentityServer
import com.github.syafiqq.daggertest002.model.entity.UserEntity
import io.reactivex.Flowable
import javax.inject.Inject

class UserManagerImpl @Inject constructor(private val server: IdentityServer) : UserManager {
    private var session: UserEntity? = null

    override fun login(email: String, password: String): Flowable<UserEntity> {
        return Flowable.defer {
            server.login(email, password).let {
                if (it != null) {
                    session = it
                    Flowable.just(it)
                } else
                    Flowable.error<UserEntity>(RuntimeException("Not Registered"))
            }
        }
    }

    override fun logout(): Flowable<UserEntity> {
        return Flowable.defer {
            val ses = session
            if (ses == null)
                Flowable.error<UserEntity>(RuntimeException("Unauthorized"))
            else {
                server.logout()
                session = null
                Flowable.just(ses)
            }
        }
    }

    override fun isLoggedIn(): Boolean {
        return session != null
    }

    override fun getUser(id: String): Flowable<UserEntity> {
        return Flowable.defer {
            if (session == null)
                Flowable.error<UserEntity>(RuntimeException("Unauthorized"))
            else
                server.getUser(id).let {
                    if (it != null) {
                        Flowable.just(it)
                    } else
                        Flowable.empty<UserEntity>()
                }
        }
    }
}