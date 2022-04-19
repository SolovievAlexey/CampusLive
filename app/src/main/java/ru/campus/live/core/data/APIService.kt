package ru.campus.live.core.data

import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*
import ru.campus.live.core.data.model.UploadResultModel
import ru.campus.live.discussion.data.model.DiscussionObject
import ru.campus.live.ribbon.data.model.RibbonModel
import ru.campus.live.location.data.model.LocationModel
import ru.campus.live.start.data.model.LoginModel

interface APIService {

    @GET("api/2.0/user.login")
    fun registration(@Query("os") os: Int): Call<LoginModel>

    @GET("api/2.0/location.get")
    fun location(
        @Query("token") token: String,
        @Query("name") name: String?
    ): Call<List<LocationModel>>

    @GET("api/2.0/location.rating")
    fun locationRating(
        @Query("location_id") locationId: Int,
        @Query("token") token: String
    ): Call<ResponseBody>

    @FormUrlEncoded
    @POST("api/2.0/wall.post")
    fun post(
        @Field("token") token: String,
        @Field("location_id") location: Int,
        @Field("message") message: String,
        @Field("attachment_id") attachment: Int
    ): Call<RibbonModel>

    @Multipart
    @POST("api/2.0/media.upload")
    fun uploadAttachmentOnServer(@Part media: MultipartBody.Part?): Call<UploadResultModel>

    @GET("api/2.0/wall.get")
    fun wallGet(
        @Query("token") token: String,
        @Query("location_id") location: Int,
        @Query("offset") offset: Int
    ): Call<ArrayList<RibbonModel>>

    @GET("api/2.0/wall.vote")
    fun publicationVote(
        @Query("token") token: String,
        @Query("vote") vote: Int,
        @Query("object_id") objectId: Int
    ): Call<ResponseBody>

    @GET("api/2.0/complaint")
    fun complaint(
        @Query("object_type") objectType: Int,
        @Query("object_id") objectId: Int
    ): Call<ResponseBody>

    @GET("api/2.0/comments.get")
    fun discussion(
        @Query("token") token: String,
        @Query("publication_id") publicationId: Int
    ): Call<ArrayList<DiscussionObject>>

    @FormUrlEncoded
    @POST("api/2.0/comment.post")
    fun commentCreate(
        @Field("token") token: String,
        @Field("icon_id") icon: Int,
        @Field("message") message: String,
        @Field("attachment_id") attachmentId: Int,
        @Field("parent") parent: Int,
        @Field("answered") answered: Int,
        @Field("publication_id") publicationId: Int,
    ): Call<DiscussionObject>

    @GET("api/2.0/comment.vote")
    fun voteComment(
        @Query("token") token: String,
        @Query("vote") vote: Int,
        @Query("object_id") objectId: Int
    ): Call<ResponseBody>

}