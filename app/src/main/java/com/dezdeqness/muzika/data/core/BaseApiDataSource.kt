package com.dezdeqness.muzika.data.core

import com.dezdeqness.innertube.models.others.YouTubeLocale
import com.dezdeqness.muzika.data.mapper.ApiMapper
import java.util.Locale

abstract class BaseApiDataSource(
    protected val apiMapper: ApiMapper,
) {

    protected suspend fun <T> tryWithCatch(block: suspend () -> Result<T>) = try {
        block()
    } catch (exception: Throwable) {
        Result.failure(exception)
    }

    companion object {
        var locale = YouTubeLocale(
            gl = Locale.getDefault().country,
            hl = Locale.getDefault().toLanguageTag()
        )
        var visitorData: String = "CgtsZG1ySnZiQWtSbyiMjuGSBg%3D%3D"
    }

}
