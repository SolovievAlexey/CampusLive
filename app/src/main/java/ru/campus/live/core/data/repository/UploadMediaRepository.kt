package ru.campus.live.core.data.repository

import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import ru.campus.live.core.data.APIService
import ru.campus.live.core.data.source.CloudDataSource
import ru.campus.live.core.data.source.ErrorDataSource
import ru.campus.live.core.data.model.ResponseObject
import ru.campus.live.core.data.model.UploadResultModel
import ru.campus.live.core.domain.PreparationMediaUseCase
import ru.campus.live.gallery.data.model.GalleryDataObject
import java.io.File
import javax.inject.Inject

class UploadMediaRepository @Inject constructor(
    private val apiService: APIService,
    private val errorDataSource: ErrorDataSource,
    private val preparation: PreparationMediaUseCase
) : IUploadMediaRepository {

    override fun upload(params: GalleryDataObject): ResponseObject<UploadResultModel> {
        val myFileUpload: File? = preparation.execute(params.fullPath, params.realOrientation)
        if (myFileUpload != null) {
            val reqFile: RequestBody = RequestBody.create(MediaType.parse("image/*"), myFileUpload)
            val body = MultipartBody.Part.createFormData("media", myFileUpload.name, reqFile)
            val call = apiService.uploadAttachmentOnServer(body)
            return CloudDataSource<UploadResultModel>(errorDataSource).execute(call)
        } else {
            return ResponseObject.Failure(errorDataSource.get())
        }
    }

}