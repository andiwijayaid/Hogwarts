package id.andiwijaya.hogwarts.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import id.andiwijaya.hogwarts.core.Constants.DEFAULT_PAGE_SIZE
import id.andiwijaya.hogwarts.data.local.HogwartsDatabase
import id.andiwijaya.hogwarts.data.mediator.HogwartsRemoteMediator
import id.andiwijaya.hogwarts.data.remote.service.HogwartsRemoteDataSource
import id.andiwijaya.hogwarts.domain.model.Character
import id.andiwijaya.hogwarts.domain.repository.HogwartsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HogwartsRepositoryImpl @Inject constructor(
    private val hogwartsDatabase: HogwartsDatabase,
    private val remoteDataSource: HogwartsRemoteDataSource
) : HogwartsRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getCharacters(house: String): Flow<PagingData<Character>> = Pager(
        config = PagingConfig(pageSize = DEFAULT_PAGE_SIZE),
        remoteMediator = HogwartsRemoteMediator(hogwartsDatabase, remoteDataSource, house),
        pagingSourceFactory = { hogwartsDatabase.characterDao().getCharacters(house) }
    ).flow

}