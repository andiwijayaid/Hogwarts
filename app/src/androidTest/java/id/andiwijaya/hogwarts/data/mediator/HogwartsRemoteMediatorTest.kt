package id.andiwijaya.hogwarts.data.mediator

import androidx.paging.*
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import id.andiwijaya.hogwarts.core.Constants.House.GRYFFINDOR
import id.andiwijaya.hogwarts.data.local.HogwartsDatabase
import id.andiwijaya.hogwarts.data.remote.PotterDbApi
import id.andiwijaya.hogwarts.data.remote.dto.response.GetCharactersResponse
import id.andiwijaya.hogwarts.domain.model.Character
import id.andiwijaya.hogwarts.util.DataDummy.dummyGetCharactersResponse
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Response
import java.io.IOException
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@ExperimentalPagingApi
@RunWith(AndroidJUnit4::class)
class HogwartsRemoteMediatorTest {

    private val mockApi = mockk<PotterDbApi>()

    private val mockDb = Room.inMemoryDatabaseBuilder(
        InstrumentationRegistry.getInstrumentation().context, HogwartsDatabase::class.java
    ).build()

    private val pagingState = PagingState<Int, Character>(
        listOf(),
        null,
        PagingConfig(pageSize = 10),
        10
    )

    @After
    fun tearDown() {
        mockDb.clearAllTables()
        mockDb.close()
    }

    private lateinit var remoteMediator: HogwartsRemoteMediator

    @Before
    fun setup() {
        remoteMediator = HogwartsRemoteMediator(mockDb, mockApi)
    }

    @Test
    fun getCharactersReturnsSuccessResultWhenMoreDataIsPresent() = runBlocking {
        val response = Response.success(dummyGetCharactersResponse)
        coEvery { mockApi.getCharacters(GRYFFINDOR, 1) } returns response

        remoteMediator.setRequest(false, GRYFFINDOR)

        val result = remoteMediator.load(LoadType.REFRESH, pagingState)
        assertTrue { result is RemoteMediator.MediatorResult.Success }
        assertFalse { (result as RemoteMediator.MediatorResult.Success).endOfPaginationReached }
    }

    @Test
    fun getCharactersReturnsSuccessAndEndOfPaginationWhenNoMoreData() = runBlocking {
        val response = Response.success(GetCharactersResponse(emptyList()))
        coEvery { mockApi.getCharacters(GRYFFINDOR, 1) } returns response

        remoteMediator.setRequest(false, GRYFFINDOR)

        val result = remoteMediator.load(LoadType.REFRESH, pagingState)
        assertTrue { result is RemoteMediator.MediatorResult.Success }
        assertTrue { (result as RemoteMediator.MediatorResult.Success).endOfPaginationReached }
    }

    @Test
    fun getCharactersReturnsErrorResultWhenErrorOccurs() = runBlocking {
        coEvery { mockApi.getCharacters(GRYFFINDOR, 1) } throws IOException()

        remoteMediator.setRequest(false, GRYFFINDOR)

        val result = remoteMediator.load(LoadType.REFRESH, pagingState)
        assertTrue { result is RemoteMediator.MediatorResult.Error }
    }

    @Test
    fun getCharactersByNameReturnsSuccessResultWhenMoreDataIsPresent() = runBlocking {
        val response = Response.success(dummyGetCharactersResponse)
        coEvery { mockApi.getCharactersByName(GRYFFINDOR, 1) } returns response

        remoteMediator.setRequest(true, GRYFFINDOR)

        val result = remoteMediator.load(LoadType.REFRESH, pagingState)
        assertTrue { result is RemoteMediator.MediatorResult.Success }
        assertFalse { (result as RemoteMediator.MediatorResult.Success).endOfPaginationReached }
    }

    @Test
    fun getCharactersByNameReturnsSuccessAndEndOfPaginationWhenNoMoreData() = runBlocking {
        val response = Response.success(GetCharactersResponse(emptyList()))
        coEvery { mockApi.getCharactersByName(GRYFFINDOR, 1) } returns response

        remoteMediator.setRequest(true, GRYFFINDOR)

        val result = remoteMediator.load(LoadType.REFRESH, pagingState)
        assertTrue { result is RemoteMediator.MediatorResult.Success }
        assertTrue { (result as RemoteMediator.MediatorResult.Success).endOfPaginationReached }
    }

    @Test
    fun getCharactersByNameReturnsErrorResultWhenErrorOccurs() = runBlocking {
        coEvery { mockApi.getCharactersByName(GRYFFINDOR, 1) } throws IOException()

        remoteMediator.setRequest(true, GRYFFINDOR)

        val result = remoteMediator.load(LoadType.REFRESH, pagingState)
        assertTrue { result is RemoteMediator.MediatorResult.Error }
    }
}