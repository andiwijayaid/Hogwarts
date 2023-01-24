package id.andiwijaya.hogwarts.domain.repository

import androidx.paging.PagingData
import id.andiwijaya.hogwarts.domain.model.Character
import kotlinx.coroutines.flow.Flow

interface HogwartsRepository {
    fun getCharacters(house: String): Flow<PagingData<Character>>
}