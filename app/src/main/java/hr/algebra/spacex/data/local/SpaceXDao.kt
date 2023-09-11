package hr.algebra.spacex.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface SpaceXDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLaunches(
        launchEntities: List<LaunchEntity>
    )

    @Query("""SELECT * FROM launchentity""")
    suspend fun getLaunches(): List<LaunchEntity>
}