package com.rafaltrzcinski.dribshots.rest.model

import com.google.gson.annotations.SerializedName
import paperparcel.PaperParcel
import paperparcel.PaperParcelable

@PaperParcel
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
        ): PaperParcelable {

    companion object {
        @JvmField val CREATOR = PaperParcelShot.CREATOR
    }
}


@PaperParcel
data class Images(
        val hidpi: String? = "",
        val normal: String? = "",
        val teaser: String? = ""
): PaperParcelable {
    companion object {
        @JvmField val CREATOR = PaperParcelImages.CREATOR
    }
}

@PaperParcel
data class User(
        val id: Int = 0,
        val name: String = "",
        @SerializedName("avatar_url") val avatarUrl: String = ""
): PaperParcelable {
    companion object {
        @JvmField val CREATOR = PaperParcelUser.CREATOR
    }
}

@PaperParcel
data class Team(
        val id: Int = 0,
        val name: String? = "",
        @SerializedName("avatar_url") val avatarUrl: String? = ""
): PaperParcelable {
    companion object {
        @JvmField val CREATOR = PaperParcelTeam.CREATOR
    }
}