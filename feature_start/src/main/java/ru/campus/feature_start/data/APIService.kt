package ru.campus.feature_start.data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import ru.campus.feature_start.data.model.LoginModel

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 12.05.2022 20:18
 */

interface APIService {

    @GET("api/2.0/user.login")
    fun registration(@Query("os") os: Int): Call<LoginModel>

}