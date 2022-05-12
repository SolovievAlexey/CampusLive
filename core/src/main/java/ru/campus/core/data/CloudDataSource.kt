package ru.campus.core.data

import retrofit2.Call

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 12.05.2022 20:20
 */

class CloudDataSource<T>() {

    fun execute(call: Call<T>): ResponseObject<T> {
        return try {
            val response = call.execute()
            if (response.code() == 200)
                ResponseObject.Success(data = response.body()!!)
            else
                ResponseObject.Failure(code = response.code())
        } catch (e: Exception) {
            ResponseObject.Failure(code = 0)
        }
    }

}