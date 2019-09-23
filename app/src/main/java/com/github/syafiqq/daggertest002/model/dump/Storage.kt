package com.github.syafiqq.daggertest002.model.dump

import com.github.syafiqq.daggertest002.model.entity.UserEntity
import com.github.syafiqq.daggertest002.model.entity.UserEntityImpl

object Storage {
    val users: Map<String, UserEntity> by lazy {
        mapOf(
            "1" to UserEntityImpl("1", "name-1", "mail1@mail.com", "pass1"),
            "2" to UserEntityImpl("2", "name-2", "mail2@mail.com", "pass2"),
            "3" to UserEntityImpl("3", "name-3", "mail3@mail.com", "pass3"),
            "4" to UserEntityImpl("4", "name-4", "mail4@mail.com", "pass4")
        )
    }
}