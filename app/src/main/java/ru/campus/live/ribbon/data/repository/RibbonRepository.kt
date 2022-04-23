package ru.campus.live.ribbon.data.repository

import android.util.Log
import okhttp3.ResponseBody
import ru.campus.live.core.data.APIService
import ru.campus.live.core.data.source.CloudDataSource
import ru.campus.live.core.data.source.ErrorDataSource
import ru.campus.live.core.data.source.UserDataSource
import ru.campus.live.core.data.model.ResponseObject
import ru.campus.live.core.data.model.VoteModel
import ru.campus.live.ribbon.data.model.RibbonPostModel
import ru.campus.live.ribbon.data.model.RibbonModel
import ru.campus.live.ribbon.data.model.RibbonViewType
import javax.inject.Inject

class RibbonRepository @Inject constructor(
    private val apiService: APIService,
    private val errorDataSource: ErrorDataSource,
    private val userDataSource: UserDataSource,
    private val cashDataSource: ICashDataSource
) : IRibbonRepository {

    override fun getCash() {
        Log.d("MyLog", "Получаем данные с БД")
    }

    override fun postCash(model: ArrayList<RibbonModel>) {
        cashDataSource.post(model)
    }

    override fun get(offset: Int): ResponseObject<ArrayList<RibbonModel>> {
        val call = apiService.wallGet(userDataSource.token(), userDataSource.location().locationId, offset)
        return CloudDataSource<ArrayList<RibbonModel>>(errorDataSource = errorDataSource).execute(
            call)
    }

    override fun post(params: RibbonPostModel): ResponseObject<RibbonModel> {
        val call = apiService.post(
            userDataSource.token(), userDataSource.location().locationId,
            params.message, params.attachment
        )
        return CloudDataSource<RibbonModel>(errorDataSource = errorDataSource).execute(call)
    }

    override fun vote(params: VoteModel) {
        val call = apiService.publicationVote(
            token = userDataSource.token(), vote = params.vote,
            objectId = params.id
        )
        CloudDataSource<ResponseBody>(errorDataSource = errorDataSource).execute(call)
    }

    override fun complaint(id: Int) {
        val call = apiService.complaint(RibbonViewType.PUBLICATION.ordinal, id)
        CloudDataSource<ResponseBody>(errorDataSource = errorDataSource).execute(call)
    }

}