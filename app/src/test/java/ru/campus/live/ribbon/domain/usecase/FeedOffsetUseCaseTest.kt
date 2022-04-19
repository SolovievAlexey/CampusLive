package ru.campus.live.ribbon.domain.usecase

import org.junit.Assert
import org.junit.Test
import ru.campus.live.ribbon.data.model.RibbonModel
import ru.campus.live.ribbon.data.model.RibbonViewType

class FeedOffsetUseCaseTest {

    @Test
    fun `counts the number of records with type FeedViewType = PUBLICATION`() {
        val model = ArrayList<RibbonModel>()
        model.add(RibbonModel(viewType = RibbonViewType.HEADING))
        model.add(RibbonModel(viewType = RibbonViewType.PUBLICATION))
        model.add(RibbonModel(viewType = RibbonViewType.PUBLICATION))

        val actual = FeedOffsetUseCase().execute(model)
        val expected = 2
        Assert.assertEquals(expected, actual)
    }

}