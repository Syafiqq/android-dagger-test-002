package com.github.syafiqq.daggertest002.custom.dagger.android

import dagger.android.AndroidInjector
import dagger.internal.Beta

/** Provides an [AndroidInjector].  */
@Beta
interface HasAndroidInjector : dagger.android.HasAndroidInjector {
    /** Returns an [AndroidInjector].  */
    fun androidInjector(cls: Class<*>): AndroidInjector<Any>?

    fun clear(cls: Class<*>)

    fun exists(cls: Class<*>): Boolean
}
