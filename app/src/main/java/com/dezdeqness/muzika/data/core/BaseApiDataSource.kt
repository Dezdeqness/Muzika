package com.dezdeqness.muzika.data.core

abstract class BaseApiDataSource(
) {

    protected suspend fun <T> tryWithCatch(block: suspend () -> Result<T>) = try {
        block()
    } catch (exception: Throwable) {
        Result.failure(exception)
    }

}
