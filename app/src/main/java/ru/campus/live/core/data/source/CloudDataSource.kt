package ru.campus.live.core.data.source

import retrofit2.Call
import ru.campus.live.core.data.model.ResponseObject

class CloudDataSource<T>(private val errorDataSource: ErrorDataSource) {

    fun execute(call: Call<T>): ResponseObject<T> {
        return try {
            val response = call.execute()
            if (response.code() == 200)
                ResponseObject.Success(data = response.body()!!)
            else
                ResponseObject.Failure(error = errorDataSource.get(response.code()))
        } catch (e: Exception) {
            ResponseObject.Failure(error = errorDataSource.get())
        }
    }

}