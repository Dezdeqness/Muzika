package com.dezdeqness.shared.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    @SerialName("avatar_url")
    val avatarUrl: String,
    val id: Long,
    val urn: String,
    val kind: String,
    @SerialName("permalink_url")
    val permalinkUrl: String,
    val uri: String,
    val username: String,
    val permalink: String,
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("last_modified")
    val lastModified: String,
    @SerialName("first_name")
    val firstName: String?,
    @SerialName("last_name")
    val lastName: String?,
    @SerialName("full_name")
    val fullName: String,
    val city: String?,
    val description: String?,
    val country: String?,
    @SerialName("track_count")
    val trackCount: Long,
    @SerialName("public_favorites_count")
    val publicFavoritesCount: Long,
    @SerialName("reposts_count")
    val repostsCount: Long,
    @SerialName("followers_count")
    val followersCount: Long,
    @SerialName("followings_count")
    val followingsCount: Long,
    val plan: String,
    @SerialName("myspace_name")
    val myspaceName: String?,
    @SerialName("discogs_name")
    val discogsName: String?,
    @SerialName("website_title")
    val websiteTitle: String?,
    val website: String?,
    @SerialName("comments_count")
    val commentsCount: Long,
    val online: Boolean,
    @SerialName("likes_count")
    val likesCount: Long,
    @SerialName("playlist_count")
    val playlistCount: Long,
)
