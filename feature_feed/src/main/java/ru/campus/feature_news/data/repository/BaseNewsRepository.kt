package ru.campus.feature_news.data.repository

import okhttp3.ResponseBody
import ru.campus.core.data.CloudDataSource
import ru.campus.core.data.ResponseObject
import ru.campus.core.data.UserDataStore
import ru.campus.feature_news.data.APIService
import ru.campus.feature_news.data.db.CashDataSource
import ru.campus.feature_news.data.model.FeedModel
import ru.campus.feature_news.data.model.FeedPostModel
import ru.campus.feature_news.data.model.VoteModel
import javax.inject.Inject

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 14.05.2022 20:46
 */

class BaseNewsRepository @Inject constructor(
    private val apiService: APIService,
    private val cashDataSource: CashDataSource,
    private val userDataStore: UserDataStore
) : NewsRepository {

    override fun cache(): ArrayList<FeedModel> {
        return cashDataSource.get()
    }

    override fun get(offset: Int): ResponseObject<ArrayList<FeedModel>> {
        val call = apiService.get(
            token = userDataStore.token(),
            location = userDataStore.location(),
            offset = offset
        )
        return CloudDataSource<ArrayList<FeedModel>>().execute(call = call)
    }

    override fun save(model: ArrayList<FeedModel>) {
        cashDataSource.post(model)
    }

    override fun post(params: FeedPostModel): ResponseObject<FeedModel> {
        val call = apiService.post(
            token = userDataStore.token(),
            location = userDataStore.location(),
            message = params.message,
            attachment = params.attachment
        )
        return CloudDataSource<FeedModel>().execute(call = call)
    }

    override fun vote(params: VoteModel) {
        val call = apiService.vote(
            token = userDataStore.token(),
            id = params.id,
            vote = params.vote
        )
        CloudDataSource<ResponseBody>().execute(call = call)
    }

    override fun complaint(id: Int) {
        val call = apiService.complaint(id = id)
        CloudDataSource<ResponseBody>().execute(call = call)
    }

}