package com.hmdlong14.cryptocurrency.data.repository.sources.remote.callback

import java.lang.Exception

interface ResultCallback<T> {
    fun onSuccess(result: T)
    fun onFailed(exception: Exception)
}