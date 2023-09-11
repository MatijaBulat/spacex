package hr.algebra.spacex.data.remote.dto.launches


import com.google.gson.annotations.SerializedName

data class Patch(
    @SerializedName("large")
    val large: String?,
    @SerializedName("small")
    val small: String?
)