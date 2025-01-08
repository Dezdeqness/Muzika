package com.dezdeqness.muzika.di

import com.dezdeqness.muzika.data.datasource.NextApiDataSource
import com.dezdeqness.muzika.data.datasource.NextApiDataSourceImpl
import com.dezdeqness.muzika.data.datasource.PlayerApiDataSource
import com.dezdeqness.muzika.data.datasource.PlayerApiDataSourceImpl
import com.dezdeqness.muzika.data.datasource.SearchApiDataSource
import com.dezdeqness.muzika.data.datasource.SearchApiDataSourceImpl
import org.koin.dsl.module


val repositoryModule = module {
    factory<NextApiDataSource> {
        NextApiDataSourceImpl(
            nextService = get(),
            apiMapper = get(),
        )
    }

    factory<PlayerApiDataSource> {
        PlayerApiDataSourceImpl(
            playerService = get(),
            apiMapper = get(),
        )
    }

    factory<SearchApiDataSource> {
        SearchApiDataSourceImpl(
            searchService = get(),
            apiMapper = get(),
        )
    }
}
