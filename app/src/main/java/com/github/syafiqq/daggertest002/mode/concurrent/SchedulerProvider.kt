package com.github.syafiqq.daggertest002.mode.concurrent

import io.reactivex.Scheduler

interface SchedulerProvider {
    fun computation(): Scheduler

    fun io(): Scheduler

    fun ui(): Scheduler
}