package com.kouki.friends.app

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class DefaultDispatchers : CoroutineDispatchers {
    override val background = Dispatchers.IO
}
