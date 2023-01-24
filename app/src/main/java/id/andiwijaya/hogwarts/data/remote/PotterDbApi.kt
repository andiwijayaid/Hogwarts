package id.andiwijaya.hogwarts.data.remote

import id.andiwijaya.hogwarts.core.Constants.DEFAULT_PAGE_SIZE
import id.andiwijaya.hogwarts.data.remote.dto.response.GetCharactersResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PotterDbApi {

    @GET("characters")
    suspend fun getCharacters(
        @Query("filter[house_cont_any]") house: String,
        @Query("page[number]") pageNumber: Int,
        @Query("page[size]") size: Int? = DEFAULT_PAGE_SIZE
    ): Response<GetCharactersResponse>

}