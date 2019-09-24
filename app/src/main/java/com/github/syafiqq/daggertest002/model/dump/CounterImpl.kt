package com.github.syafiqq.daggertest002.model.dump

import java.util.concurrent.atomic.AtomicInteger

class CounterImpl(private val init: Int = 0) : CounterContract {
    private val c = AtomicInteger(init)

    override val value: Int
        get() = c.incrementAndGet()
}