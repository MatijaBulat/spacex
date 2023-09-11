package hr.algebra.spacex.data.local

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(
    entities = [LaunchEntity::class],
    version = 1,
    exportSchema = false
)
abstract class SpaceXDatabase : RoomDatabase() {
    abstract val dao: SpaceXDao
}