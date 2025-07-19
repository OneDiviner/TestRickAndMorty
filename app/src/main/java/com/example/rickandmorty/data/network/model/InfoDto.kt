package com.example.rickandmorty.data.network.model

import com.google.gson.annotations.SerializedName

data class InfoDto(
    @SerializedName("count")
    val count: Int?,
    @SerializedName("pages")
    val pages: Int?,
    @SerializedName("next")
    val next: String?,
    @SerializedName("prev")
    val prev: String?
) {
    companion object {
        val EMPTY = InfoDto(
            count = 0,
            pages = 0,
            next = null,
            prev = null
        )
    }
}