package com.dezdeqness.muzika.data.datasource

import com.dezdeqness.innertube.models.others.WatchEndpoint
import com.dezdeqness.innertube.models.pages.NextResult

interface NextApiDataSource {

    suspend fun next(endpoint: WatchEndpoint, continuation: String? = null): Result<NextResult>
}
