package id.andiwijaya.hogwarts.data.remote.service

import id.andiwijaya.hogwarts.core.Result
import id.andiwijaya.hogwarts.core.base.BaseDataSource
import id.andiwijaya.hogwarts.data.remote.PotterDbApi
import id.andiwijaya.hogwarts.data.remote.dto.response.GetCharactersResponse
import javax.inject.Inject

class HogwartsRemoteDataSource @Inject constructor(
    private val api: PotterDbApi
) : BaseDataSource() {

    suspend fun getCharacters(
        house: String,
        pageNumber: Int,
        size: Int? = null
    ): Result<GetCharactersResponse> {
        return getResultWithSingleObject { api.getCharacters(house, pageNumber, size) }
    }

    suspend fun getCharactersByName(
        name: String,
        pageNumber: Int,
        size: Int? = null
    ): Result<GetCharactersResponse> {
        return getResultWithSingleObject {
            api.getCharactersByName(name, pageNumber, size)
        }
    }

}