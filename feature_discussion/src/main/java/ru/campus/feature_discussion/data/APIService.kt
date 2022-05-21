package ru.campus.feature_discussion.data

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*
import ru.campus.feature_discussion.data.model.DiscussionModel

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 21.05.2022 16:12
 */

interface APIService {

    @GET("api/2.0/comments.get")
    fun get(
        @Query("token") token: String,
        @Query("publication_id") publicationId: Int
    ): Call<ArrayList<DiscussionModel>>

    @FormUrlEncoded
    @POST("api/2.0/comment.post")
    fun post(
        @Field("token") token: String,
        @Field("icon_id") icon: Int,
        @Field("message") message: String,
        @Field("attachment_id") attachmentId: Int,
        @Field("parent") parent: Int,
        @Field("answered") answered: Int,
        @Field("publication_id") publicationId: Int,
    ): Call<DiscussionModel>

    @GET("api/2.0/comment.vote")
    fun vote(
        @Query("token") token: String,
        @Query("vote") vote: Int,
        @Query("object_id") objectId: Int
    ): Call<ResponseBody>

    @GET("api/2.0/complaint")
    fun complaint(
        @Query("object_type") objectType: Int,
        @Query("object_id") objectId: Int
    ): Call<ResponseBody>

}