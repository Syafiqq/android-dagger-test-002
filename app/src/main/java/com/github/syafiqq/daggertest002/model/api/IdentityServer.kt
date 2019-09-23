package com.github.syafiqq.daggertest002.model.api

import com.github.syafiqq.daggertest002.model.entity.UserEntity
import io.reactivex.Flowable

interface IdentityServer {
    fun login(email: String, password: String): Flowable<UserEntity>
    fun logout()
    fun getUser(id: String): Flowable<UserEntity>
}