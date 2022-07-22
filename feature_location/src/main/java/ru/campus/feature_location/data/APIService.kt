package ru.campus.feature_location.data

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import ru.campus.feature_location.data.model.LocationModel

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 14.05.2022 16:46
 */

interface APIService {

    @GET("api/2.0/location.get")
    fun location(
        @Query("token") token: String,
        @Query("name") name: String?
    ): Call<ArrayList<LocationModel>>

    @GET("api/2.0/location.rating")
    fun locationRating(
        @Query("token") token: String,
        @Query("location_id") id: Int
    ): Call<ResponseBody>

    @GET("api/1.0/location.nearbyGet")
    fun locationNearby(
        @Query("token") token: String,
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
    ): Call<ArrayList<LocationModel>>

}