package ru.campus.live.core.domain

import org.junit.Assert
import org.junit.Test
import ru.campus.live.core.data.model.ItemVoteDataModel
import ru.campus.live.core.data.model.VoteModel

class ItemVoteEditUseCaseTest {

    /* Мне было лень писать тест под каждый кейс,
     поэтому в каждой функции по две прокерки */

    @Test
    fun like() {
        val item = ItemVoteDataModel(rating = 0, vote = 0)
        val voteObject = VoteModel(1, 1)

        val result = ItemVoteEditUseCase().execute(item, voteObject)
        val actual = result.rating
        val expected = 1

        val actualVote = result.vote
        val expectedVote = 1

        Assert.assertEquals(expected, actual)
        Assert.assertEquals(expectedVote, actualVote)
    }

    @Test
    fun dislike() {
        val item = ItemVoteDataModel(rating = 0, vote = 0)
        val voteObject = VoteModel(1, 2)

        val result = ItemVoteEditUseCase().execute(item, voteObject)
        val actual = result.rating
        val expected = -1

        val actualVote = result.vote
        val expectedVote = 2

        Assert.assertEquals(expected, actual)
        Assert.assertEquals(expectedVote, actualVote)
    }

    @Test
    fun changeToLike() {
        val item = ItemVoteDataModel(rating = -1, vote = 2)
        val voteObject = VoteModel(1, 1)

        val result = ItemVoteEditUseCase().execute(item, voteObject)
        val actualRating = result.rating
        val expectedRating = 1

        val actualVote = result.vote
        val expectedVote = 1

        Assert.assertEquals(expectedRating, actualRating)
        Assert.assertEquals(expectedVote, actualVote)
    }

    @Test
    fun changeToDislike() {
        val item = ItemVoteDataModel(rating = 1, vote = 1)
        val voteObject = VoteModel(1, 2)

        val result = ItemVoteEditUseCase().execute(item, voteObject)
        val actualRating = result.rating
        val expectedRating = -1

        val actualVote = result.vote
        val expectedVote = 2

        Assert.assertEquals(expectedRating, actualRating)
        Assert.assertEquals(expectedVote, actualVote)
    }


}