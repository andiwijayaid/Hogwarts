package id.andiwijaya.hogwarts.data.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import id.andiwijaya.hogwarts.core.Constants.INITIAL_PAGE_INDEX
import id.andiwijaya.hogwarts.core.Constants.ONE
import id.andiwijaya.hogwarts.core.Constants.PERCENT
import id.andiwijaya.hogwarts.core.Constants.ZERO
import id.andiwijaya.hogwarts.core.Status.ERROR
import id.andiwijaya.hogwarts.core.util.orTrue
import id.andiwijaya.hogwarts.core.util.wrap
import id.andiwijaya.hogwarts.data.local.HogwartsDatabase
import id.andiwijaya.hogwarts.data.remote.dto.response.toCharacters
import id.andiwijaya.hogwarts.data.remote.service.HogwartsRemoteDataSource
import id.andiwijaya.hogwarts.domain.model.Character
import id.andiwijaya.hogwarts.domain.model.RemoteKeys
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class HogwartsRemoteMediator @Inject constructor(
    private val database: HogwartsDatabase,
    private val remoteDataSource: HogwartsRemoteDataSource,
    private val keyword: String,
    private val isSearch: Boolean = false
) : RemoteMediator<Int, Character>() {

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
                remoteDataSource.getCharactersByName(keyword, page, state.config.pageSize)
            } else remoteDataSource.getCharacters(keyword, page, state.config.pageSize)
            val endOfPaginationReached = response.data?.characters?.isEmpty().orTrue()
            database.withTransaction {
                val isError = setOf(
                    response.status == ERROR,
                    isCharactersNotExistByHouse(keyword),
                    isCharactersNotExistByName(keyword)
                ).all { true }
                if (isError) MediatorResult.Error(Exception("Error")) else {
                    val prevKey = if (page == ONE) null else page - ONE
                    val nextKey = if (endOfPaginationReached) null else page + ONE
                    val keys = response.data?.characters?.map {
                        RemoteKeys(id = it.id, prevKey = prevKey, nextKey = nextKey)
                    }
                    database.remoteKeysDao().insertAll(keys.orEmpty())
                    database.characterDao()
                        .insertCharacters(response.data?.toCharacters().orEmpty())
                    MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
                }
            }
        } catch (exception: Exception) {
            MediatorResult.Error(exception)
        }
    }

    private suspend fun isCharactersNotExistByHouse(house: String) =
        database.characterDao().getNumberOfCharactersByHouse(house.wrap(PERCENT)) == ZERO

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

}