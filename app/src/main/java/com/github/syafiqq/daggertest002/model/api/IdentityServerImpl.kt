package com.github.syafiqq.daggertest002.model.api

import android.text.TextUtils
import com.github.syafiqq.daggertest002.model.dump.Storage
import com.github.syafiqq.daggertest002.model.entity.UserEntity
import io.reactivex.Flowable

class IdentityServerImpl : IdentityServer {
    val users = Storage.users
    var session: UserEntity? = null

    override fun login(email: String, password: String): Flowable<UserEntity> {
        return users.values.firstOrNull {
            TextUtils.equals(email, it.email) and TextUtils.equals(password, it.password)
        }.let {
            if (it != null) {
                session = it
                Flowable.just(it)
            } else
                Flowable.empty<UserEntity>()
        }
    }

    override fun logout() {
        session = null
    }

    override fun getUser(id: String): Flowable<UserEntity> {
        return if (session == null)
            Flowable.error<UserEntity>(RuntimeException("Unauthorized"))
        else
            users.keys.firstOrNull {
                TextUtils.equals(id, it)
            }.let {
                if (it != null) {
                    Flowable.just(it as UserEntity)
                } else
                    Flowable.empty<UserEntity>()
            }
    }
}