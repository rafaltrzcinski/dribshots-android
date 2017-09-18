package com.rafaltrzcinski.dribshots.rest.model

data class Shot(
        val id: Int = 0,
        val title: String = "",
        val description: String? = "",
        val images: Images = Images()
)

data class Images(
        val hidpi: String? = "",
        val normal: String? = "",
        val teaser: String? = ""
)