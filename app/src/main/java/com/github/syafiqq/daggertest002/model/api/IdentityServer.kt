package com.github.syafiqq.daggertest002.model.api

import com.github.syafiqq.daggertest002.model.entity.UserEntity

interface IdentityServer {
    fun login(email: String, password: String): UserEntity?
    fun logout()
    fun getUser(id: String): UserEntity?
}