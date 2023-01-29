package id.andiwijaya.hogwarts.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import id.andiwijaya.hogwarts.core.Constants.DEFAULT_PAGE_SIZE
import id.andiwijaya.hogwarts.core.Constants.PERCENT
import id.andiwijaya.hogwarts.core.util.wrap
import id.andiwijaya.hogwarts.data.local.HogwartsDatabase
import id.andiwijaya.hogwarts.data.mediator.HogwartsRemoteMediator
import id.andiwijaya.hogwarts.domain.model.Character
import id.andiwijaya.hogwarts.domain.repository.HogwartsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HogwartsRepositoryImpl @Inject constructor(
    private val hogwartsDatabase: HogwartsDatabase,
    private val hogwartsRemoteMediator: HogwartsRemoteMediator
) : HogwartsRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getCharacters(
        keyword: String,
        isSearch: Boolean
    ): Flow<PagingData<Character>> = Pager(
        config = PagingConfig(pageSize = DEFAULT_PAGE_SIZE),
        remoteMediator = hogwartsRemoteMediator.also {
            it.setRequest(isSearch, keyword.wrap(PERCENT).takeIf { isSearch } ?: keyword)
        },
        pagingSourceFactory = {
            if (isSearch) {
                hogwartsDatabase.characterDao().getCharactersByName(keyword.wrap(PERCENT))
            } else hogwartsDatabase.characterDao().getCharactersByHouse(keyword)
        }
    ).flow

}