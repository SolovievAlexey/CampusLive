package ru.campus.feature_news.data

import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 14.05.2022 20:46
 */

interface APIService {

    @GET("api/2.0/wall.get")
    fun get(
        @Query("token") token: String,
        @Query("location_id") location: Int,
        @Query("offset") offset: Int
    ): Call<ArrayList<FeedModel>>

    @FormUrlEncoded
    @POST("api/2.0/wall.post")
    fun post(
        @Field("token") token: String,
        @Field("location_id") location: Int,
        @Field("message") message: String,
        @Field("attachment_id") attachment: Int
    ): Call<FeedModel>

    @Multipart
    @POST("api/2.0/media.upload")
    fun upload(@Part media: MultipartBody.Part?): Call<UploadResultModel>

    @GET("api/2.0/wall.vote")
    fun vote(
        @Query("token") token: String,
        @Query("object_id") id: Int,
        @Query("vote") vote: Int,
    ): Call<ResponseBody>

    @GET("api/2.0/complaint")
    fun complaint(
        @Query("object_type") type: Int = 1,
        @Query("object_id") id: Int
    ): Call<ResponseBody>

}