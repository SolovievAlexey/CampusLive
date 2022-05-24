package ru.campus.file_upload.data

import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import ru.campus.core.data.CloudDataSource
import ru.campus.core.data.GalleryDataModel
import ru.campus.core.data.ResponseObject
import ru.campus.file_upload.domain.PreparationMediaUseCase
import java.io.File

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 18.05.2022 21:29
 */

class BaseUploadMediaRepository(
    private val uploadMediaService: UploadMediaService,
    private val preparation: PreparationMediaUseCase,
) : UploadMediaRepository {

    override fun upload(params: GalleryDataModel): ResponseObject<UploadResultModel> {
        val myFileUpload: File? = preparation.execute(params.fullPath, params.realOrientation)
        return if (myFileUpload != null) {
            val reqFile: RequestBody = RequestBody.create(MediaType.parse("image/*"), myFileUpload)
            val body = MultipartBody.Part.createFormData("media", myFileUpload.name, reqFile)
            val call = uploadMediaService.upload(body)
            CloudDataSource<UploadResultModel>().execute(call)
        } else {
            ResponseObject.Failure(0)
        }
    }

}