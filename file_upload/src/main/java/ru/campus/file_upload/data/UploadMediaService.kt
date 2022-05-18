package ru.campus.file_upload.data

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 18.05.2022 21:31
 */

interface UploadMediaService {

    @Multipart
    @POST("api/2.0/media.upload")
    fun upload(@Part media: MultipartBody.Part?): Call<UploadResultModel>
}