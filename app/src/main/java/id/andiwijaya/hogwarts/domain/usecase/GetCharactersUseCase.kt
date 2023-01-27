package id.andiwijaya.hogwarts.domain.usecase

import androidx.paging.PagingData
import id.andiwijaya.hogwarts.domain.model.Character
import kotlinx.coroutines.flow.Flow

interface GetCharactersUseCase {
    operator fun invoke(keyword: String, isSearch: Boolean = false): Flow<PagingData<Character>>
}