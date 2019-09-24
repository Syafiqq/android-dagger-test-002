package com.github.syafiqq.daggertest002.model.api

import android.os.SystemClock
import android.text.TextUtils
import com.github.syafiqq.daggertest002.model.dump.Storage
import com.github.syafiqq.daggertest002.model.entity.UserEntity

class IdentityServerImpl : IdentityServer {
    val users = Storage.users
    var session: UserEntity? = null

    override fun login(email: String, password: String): UserEntity? {
        SystemClock.sleep(1000)
        return users.values.firstOrNull {
            TextUtils.equals(email, it.email) and TextUtils.equals(password, it.password)
        }
    }

    override fun logout() {
        SystemClock.sleep(1000)
        session = null
    }

    override fun getUser(id: String): UserEntity? {
        SystemClock.sleep(1000)
        return if (session == null)
            throw RuntimeException("Unauthorized")
        else
            users.entries.firstOrNull {
                TextUtils.equals(id, it.key)
            }?.value
    }
}