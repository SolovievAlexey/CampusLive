package ru.campus.live.location.domain

import org.junit.After
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock
import ru.campus.live.core.data.model.ResponseObject
import ru.campus.live.core.data.model.ErrorObject
import ru.campus.live.location.data.model.LocationModel
import ru.campus.live.location.data.repository.ILocationRepository

class LocationInteractorTest {

    private val repository = mock<ILocationRepository>()

    @After
    fun tearDown() {
        Mockito.reset(repository)
    }

    @Test
    fun `if an error occurs while searching for locations, an empty list is returned`() {
        val response = ResponseObject.Failure<List<LocationModel>>(
            ErrorObject(code = 0, icon = 1, message = "")
        )
        Mockito.`when`(repository.get(null)).thenReturn(response)
        val interactor = LocationInteractor(repository)
        val actual = interactor.search(null).size
        val expected = 0
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `on successful lookup list size is not 0`() {
        val model = ArrayList<LocationModel>()
        model.add(LocationModel(1, "name", "address", 1))
        val response = ResponseObject.Success<List<LocationModel>>(model)
        Mockito.`when`(repository.get("")).thenReturn(response)

        val interactor = LocationInteractor(repository)
        val actual = interactor.search("").size
        val expected = 1

        Assert.assertEquals(expected, actual)
    }

}