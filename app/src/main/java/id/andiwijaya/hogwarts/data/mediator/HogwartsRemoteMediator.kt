package id.andiwijaya.hogwarts.data.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import id.andiwijaya.hogwarts.core.Constants.EMPTY_STRING
import id.andiwijaya.hogwarts.core.Constants.INITIAL_PAGE_INDEX
import id.andiwijaya.hogwarts.core.Constants.ONE
import id.andiwijaya.hogwarts.core.Constants.ZERO
import id.andiwijaya.hogwarts.core.util.orTrue
import id.andiwijaya.hogwarts.data.local.HogwartsDatabase
import id.andiwijaya.hogwarts.data.remote.PotterDbApi
import id.andiwijaya.hogwarts.data.remote.dto.response.toCharacters
import id.andiwijaya.hogwarts.domain.model.Character
import id.andiwijaya.hogwarts.domain.model.RemoteKeys
import org.jetbrains.annotations.TestOnly
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class HogwartsRemoteMediator @Inject constructor(
    private val database: HogwartsDatabase,
    private val api: PotterDbApi
) : RemoteMediator<Int, Character>() {

    private var isSearch = false
    private var keyword = EMPTY_STRING

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Character>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> getRemoteKeyClosestToCurrentPosition(state).let {
                it?.nextKey?.minus(ONE) ?: INITIAL_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                    ?: return MediatorResult.Success(remoteKeys != null)
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                    ?: return MediatorResult.Success(remoteKeys != null)
                nextKey
            }
        }
        return try {
            val response = if (isSearch) {
                api.getCharactersByName(keyword, page)
            } else api.getCharacters(keyword, page)
            val responseBody = response.body()
            val endOfPaginationReached = responseBody?.characters?.isEmpty().orTrue()
            database.withTransaction {
                val isError = setOf(
                    response.isSuccessful.not(),
                    isCharactersNotExistByName(keyword).takeIf { isSearch }
                        ?: isCharactersNotExistByHouse(keyword)
                ).contains(false).not()
                if (isError) MediatorResult.Error(Exception("Error")) else {
                    val prevKey = if (page == ONE) null else page - ONE
                    val nextKey = if (endOfPaginationReached) null else page + ONE
                    val keys = responseBody?.characters?.map {
                        RemoteKeys(id = it.id, prevKey = prevKey, nextKey = nextKey)
                    }
                    database.remoteKeysDao().insertAll(keys.orEmpty())
                    database.characterDao()
                        .insertCharacters(responseBody?.toCharacters().orEmpty())
                    MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
                }
            }
        } catch (exception: Exception) {
            MediatorResult.Error(exception)
        }
    }

    private suspend fun isCharactersNotExistByHouse(house: String) =
        database.characterDao().getNumberOfCharactersByHouse(house) == ZERO

    private suspend fun isCharactersNotExistByName(name: String) =
        database.characterDao().getNumberOfCharactersByName(name) == ZERO

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, Character>): RemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { data ->
            database.remoteKeysDao().getRemoteKeysId(data.id)
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, Character>): RemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { data ->
            database.remoteKeysDao().getRemoteKeysId(data.id)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Character>
    ): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                database.remoteKeysDao().getRemoteKeysId(id)
            }
        }
    }

    fun setRequest(isSearch: Boolean, keyword: String) {
        this.isSearch = isSearch
        this.keyword = keyword
    }

    @TestOnly
    suspend fun isCharactersNotExistByHouseTest(house: String) = isCharactersNotExistByHouse(house)

    @TestOnly
    suspend fun isCharactersNotExistByNameTest(house: String) = isCharactersNotExistByName(house)

    @TestOnly
    suspend fun getRemoteKeyForLastItemTest(state: PagingState<Int, Character>) =
        getRemoteKeyForLastItem(state)

    @TestOnly
    suspend fun getRemoteKeyForFirstItemTest(state: PagingState<Int, Character>) =
        getRemoteKeyForFirstItem(state)

    @TestOnly
    suspend fun getRemoteKeyClosestToCurrentPositionTest(state: PagingState<Int, Character>) =
        getRemoteKeyClosestToCurrentPosition(state)

    @TestOnly
    fun getIsSearch() = isSearch

    @TestOnly
    fun getKeyword() = keyword
}