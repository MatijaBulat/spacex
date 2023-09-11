package hr.algebra.spacex.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

data class Launch(
    val _id: Long?,
    val launchDate: String,
    val name: String,
    val details: String?,
    val launchImagePath: String?
)


