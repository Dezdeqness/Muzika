package com.dezdeqness.auth.data.provider

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.dezdeqness.auth.domain.model.TokenEntity
import kotlinx.coroutines.flow.firstOrNull
import org.koin.core.annotation.Single

@Single
class TokenDataProvider(
    private val context: Context,
) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = TOKEN_FILE_NAME)

    suspend fun getTokenData(): TokenEntity? {
        val preferences = context.dataStore.data.firstOrNull() ?: return null

        val accessToken = preferences[stringPreferencesKey(KEY_ACCESS_TOKEN)].orEmpty()
        val refreshToken = preferences[stringPreferencesKey(KEY_REFRESH_TOKEN)].orEmpty()
        val createdAt = preferences[longPreferencesKey(KEY_CREATED_AT)] ?: 0
        val expiresIn = preferences[longPreferencesKey(KEY_EXPIRES_IN)] ?: 0

        return TokenEntity(accessToken, refreshToken, createdAt, expiresIn)
    }

    suspend fun setTokenData(tokenEntity: TokenEntity) {
        context.dataStore.edit { settings ->
            settings[stringPreferencesKey(KEY_ACCESS_TOKEN)] = tokenEntity.accessToken
            settings[stringPreferencesKey(KEY_REFRESH_TOKEN)] = tokenEntity.refreshToken
            settings[longPreferencesKey(KEY_CREATED_AT)] = tokenEntity.createdIn
            settings[longPreferencesKey(KEY_EXPIRES_IN)] = tokenEntity.expiresIn
        }
    }

    companion object {
        private const val TOKEN_FILE_NAME = "token"
        private const val KEY_ACCESS_TOKEN = "access_token"
        private const val KEY_REFRESH_TOKEN = "refresh_token"
        private const val KEY_CREATED_AT = "created_at"
        private const val KEY_EXPIRES_IN = "expires_in"
    }
}