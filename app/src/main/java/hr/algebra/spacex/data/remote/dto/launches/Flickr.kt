package hr.algebra.spacex.data.remote.dto.launches


import com.google.gson.annotations.SerializedName

data class Flickr(
    @SerializedName("original")
    val original: List<String>?,
    @SerializedName("small")
    val small: List<Any>?
)