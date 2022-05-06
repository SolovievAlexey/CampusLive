package ru.campus.live.ribbon.data.repository

import okhttp3.ResponseBody
import ru.campus.live.core.data.APIService
import ru.campus.live.core.data.model.ResponseObject
import ru.campus.live.core.data.model.VoteModel
import ru.campus.live.core.data.source.CloudDataSource
import ru.campus.live.core.data.source.ErrorDataSource
import ru.campus.live.core.data.source.UserDataStore
import ru.campus.live.ribbon.data.model.RibbonModel
import ru.campus.live.ribbon.data.model.RibbonPostModel
import ru.campus.live.ribbon.data.model.RibbonViewType
import javax.inject.Inject

class RibbonRepository @Inject constructor(
    private val apiService: APIService,
    private val errorDataSource: ErrorDataSource,
    private val userDataStore: UserDataStore,
    private val cashDataSource: ICashDataSource
) : IRibbonRepository {

    override fun getCash(): ArrayList<RibbonModel> {
        return cashDataSource.get()
    }

    override fun get(offset: Int): ResponseObject<ArrayList<RibbonModel>> {
        val call =
            apiService.wallGet(userDataStore.token(), userDataStore.location().locationId, offset)
        val response =
            CloudDataSource<ArrayList<RibbonModel>>(errorDataSource = errorDataSource).execute(call)
        if (response is ResponseObject.Success && offset == 0) cashDataSource.post(response.data)
        return response
    }

    override fun post(params: RibbonPostModel): ResponseObject<RibbonModel> {
        val call = apiService.post(
            userDataStore.token(), userDataStore.location().locationId,
            params.message, params.attachment
        )
        return CloudDataSource<RibbonModel>(errorDataSource = errorDataSource).execute(call)
    }

    override fun vote(params: VoteModel) {
        val call = apiService.publicationVote(
            token = userDataStore.token(), vote = params.vote,
            objectId = params.id
        )
        CloudDataSource<ResponseBody>(errorDataSource = errorDataSource).execute(call)
    }

    override fun complaint(id: Int) {
        val call = apiService.complaint(RibbonViewType.PUBLICATION.ordinal, id)
        CloudDataSource<ResponseBody>(errorDataSource = errorDataSource).execute(call)
    }

}