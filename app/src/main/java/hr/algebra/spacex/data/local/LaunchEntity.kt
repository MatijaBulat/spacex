package hr.algebra.spacex.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LaunchEntity(
    @PrimaryKey(autoGenerate = true)
    val _id: Long? = null,
    val launchDate: String,
    val name: String,
    val details: String?,
    val launchImageUrl: String,
    var launchImagePath: String?
)
