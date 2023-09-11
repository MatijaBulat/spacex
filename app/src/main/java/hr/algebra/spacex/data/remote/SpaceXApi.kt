package hr.algebra.spacex.data.remote

import hr.algebra.spacex.data.remote.dto.launches.LaunchDtoItem
import retrofit2.http.GET

interface SpaceXApi {
    @GET("/v4/launches")
    suspend fun getLaunches(): List<LaunchDtoItem>
}

