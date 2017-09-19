package com.rafaltrzcinski.dribshots.rest.model

import com.google.gson.annotations.SerializedName

data class Shot(
        val id: Int = 0,
        val title: String = "",
        val description: String? = "",
        val images: Images = Images(),
        val user: User = User(),
        val team: Team? = Team(),
        @SerializedName("views_count") val viewsCount: Int = 0,
        @SerializedName("likes_count") val likesCount: Int = 0,
        @SerializedName("comments_count") val commentsCount: Int = 0
        )

data class Images(
        val hidpi: String? = "",
        val normal: String? = "",
        val teaser: String? = ""
)

data class User(
        val id: Int = 0,
        val name: String = "",
        @SerializedName("avatar_url") val avatarUrl: String = ""
)

data class Team(
        val id: Int = 0,
        val name: String? = "",
        @SerializedName("avatar_url") val avatarUrl: String? = ""
)