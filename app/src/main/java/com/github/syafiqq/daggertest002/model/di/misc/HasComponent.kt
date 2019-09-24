package com.github.syafiqq.daggertest002.model.di.misc

import dagger.android.HasAndroidInjector

interface HasComponent<T, U> : HasAndroidInjector {
    fun aquire(): T
    fun exist(): Boolean
    fun clear(): Unit
}