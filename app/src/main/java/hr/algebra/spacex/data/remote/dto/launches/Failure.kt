package hr.algebra.spacex.data.remote.dto.launches


import com.google.gson.annotations.SerializedName

data class Failure(
    @SerializedName("altitude")
    val altitude: Int?,
    @SerializedName("reason")
    val reason: String,
    @SerializedName("time")
    val time: Int
)