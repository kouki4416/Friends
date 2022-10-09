package com.kouki.friends.app

import kotlinx.coroutines.Dispatchers

class TestDispatchers : CoroutineDispatchers {
    override val background = Dispatchers.Unconfined
}