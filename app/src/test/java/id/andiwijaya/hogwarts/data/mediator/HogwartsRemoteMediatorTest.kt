package id.andiwijaya.hogwarts.data.mediator

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import id.andiwijaya.hogwarts.data.local.HogwartsDatabase
import id.andiwijaya.hogwarts.data.remote.PotterDbApi
import id.andiwijaya.hogwarts.domain.model.Character
import id.andiwijaya.hogwarts.domain.model.RemoteKeys
import id.andiwijaya.hogwarts.util.DataDummy.dummyCharacters
import id.andiwijaya.hogwarts.util.DataDummy.faker
import id.andiwijaya.hogwarts.util.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import kotlin.random.Random

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class HogwartsRemoteMediatorTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val mockDatabase = mock<HogwartsDatabase>()
    private val mockApi = mock<PotterDbApi>()

    private lateinit var remoteMediator: HogwartsRemoteMediator

    @Before
    fun setup() = runTest {
        whenever(mockDatabase.characterDao()).thenReturn(mock())
        whenever(mockDatabase.remoteKeysDao()).thenReturn(mock())
        remoteMediator = HogwartsRemoteMediator(mockDatabase, mockApi)
    }

    @Test
    fun `isCharactersNotExistByHouse should return true when there is no record`() = runTest {
        whenever(mockDatabase.characterDao().getNumberOfCharactersByHouse("")).thenReturn(0)
        val result = remoteMediator.isCharactersNotExistByHouseTest("")
        assertThat(result).isTrue()
    }

    @Test
    fun `isCharactersNotExistByHouse should return false when there is record(s)`() = runTest {
        whenever(mockDatabase.characterDao().getNumberOfCharactersByHouse(""))
            .thenReturn(Random.nextInt(0, 10))
        val result = remoteMediator.isCharactersNotExistByHouseTest("")
        assertThat(result).isFalse()
    }

    @Test
    fun `isCharactersNotExistByName should return true when there is no record`() = runTest {
        whenever(mockDatabase.characterDao().getNumberOfCharactersByName("")).thenReturn(0)
        val result = remoteMediator.isCharactersNotExistByNameTest("")
        assertThat(result).isTrue()
    }

    @Test
    fun `isCharactersNotExistByName should return false when there is record(s)`() = runTest {
        whenever(mockDatabase.characterDao().getNumberOfCharactersByName(""))
            .thenReturn(Random.nextInt(0, 10))
        val result = remoteMediator.isCharactersNotExistByNameTest("")
        assertThat(result).isFalse()
    }

    @Test
    fun `getRemoteKeyForLastItem has last item`() = runTest {
        val dummyCharacters = dummyCharacters
        whenever(mockDatabase.remoteKeysDao().getRemoteKeysId(dummyCharacters.last().id))
            .thenReturn(RemoteKeys(dummyCharacters.last().id, 0, 1))

        val pagingState = PagingState(
            pages = listOf(PagingSource.LoadResult.Page(dummyCharacters, 0, 1)),
            anchorPosition = 1,
            config = PagingConfig(10),
            leadingPlaceholderCount = 10
        )

        val result = remoteMediator.getRemoteKeyForLastItemTest(pagingState)
        assertThat(result).isNotNull()
        assertThat(result).isEqualTo(RemoteKeys(dummyCharacters.last().id, 0, 1))
    }

    @Test
    fun `getRemoteKeyForLastItem has no items`() = runTest {
        val pagingState = PagingState<Int, Character>(
            pages = listOf(),
            anchorPosition = 1,
            config = PagingConfig(10),
            leadingPlaceholderCount = 10
        )

        val result = remoteMediator.getRemoteKeyForLastItemTest(pagingState)
        assertThat(result).isNull()
    }

    @Test
    fun `getRemoteKeyForFirstItem has last item`() = runTest {
        val dummyCharacters = dummyCharacters
        whenever(mockDatabase.remoteKeysDao().getRemoteKeysId(dummyCharacters.first().id))
            .thenReturn(RemoteKeys(dummyCharacters.first().id, 0, 1))

        val pagingState = PagingState(
            pages = listOf(PagingSource.LoadResult.Page(dummyCharacters, 0, 1)),
            anchorPosition = 1,
            config = PagingConfig(10),
            leadingPlaceholderCount = 10
        )

        val result = remoteMediator.getRemoteKeyForFirstItemTest(pagingState)
        assertThat(result).isNotNull()
        assertThat(result).isEqualTo(RemoteKeys(dummyCharacters.first().id, 0, 1))
    }

    @Test
    fun `getRemoteKeyForFirstItem has no items`() = runTest {
        val pagingState = PagingState<Int, Character>(
            pages = listOf(),
            anchorPosition = 1,
            config = PagingConfig(10),
            leadingPlaceholderCount = 10
        )

        val result = remoteMediator.getRemoteKeyForFirstItemTest(pagingState)
        assertThat(result).isNull()
    }

    @Test
    fun `getRemoteKeyClosestToCurrentPosition has last item`() = runTest {
        val dummyCharacters = dummyCharacters
        whenever(mockDatabase.remoteKeysDao().getRemoteKeysId(dummyCharacters.random().id))
            .thenReturn(RemoteKeys(dummyCharacters.random().id, 0, 1))

        val pagingState = PagingState(
            pages = listOf(PagingSource.LoadResult.Page(dummyCharacters, 0, 1)),
            anchorPosition = 1,
            config = PagingConfig(10),
            leadingPlaceholderCount = 10
        )

        val result = remoteMediator.getRemoteKeyClosestToCurrentPositionTest(pagingState)
        assertThat(result).isNotNull()
        assertThat(result).isEqualTo(RemoteKeys(dummyCharacters.random().id, 0, 1))
    }

    @Test
    fun `getRemoteKeyClosestToCurrentPosition has no items`() = runTest {
        val pagingState = PagingState<Int, Character>(
            pages = listOf(),
            anchorPosition = 1,
            config = PagingConfig(10),
            leadingPlaceholderCount = 10
        )

        val result = remoteMediator.getRemoteKeyClosestToCurrentPositionTest(pagingState)
        assertThat(result).isNull()
    }

    @Test
    fun `when setRequest not invoke it should return default value`() {
        assertThat(remoteMediator.getIsSearch()).isFalse()
        assertThat(remoteMediator.getKeyword()).isEqualTo("")
    }

    @Test
    fun `setRequest should set isSearch and keyword value`() {
        val keyword = faker.app.name()
        remoteMediator.setRequest(true, keyword)

        assertThat(remoteMediator.getIsSearch()).isTrue()
        assertThat(remoteMediator.getKeyword()).isEqualTo(keyword)
    }

}