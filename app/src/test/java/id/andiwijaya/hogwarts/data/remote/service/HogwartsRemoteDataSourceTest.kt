package id.andiwijaya.hogwarts.data.remote.service

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.*
import id.andiwijaya.hogwarts.core.Status
import id.andiwijaya.hogwarts.data.remote.PotterDbApi
import id.andiwijaya.hogwarts.data.remote.dto.response.GetCharactersResponse
import id.andiwijaya.hogwarts.util.DataDummy.dummyGetCharactersResponse
import id.andiwijaya.hogwarts.util.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
class HogwartsRemoteDataSourceTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var hogwartsRemoteDataSource: HogwartsRemoteDataSource
    private val api = mock<PotterDbApi>()

    @Before
    fun setup() {
        hogwartsRemoteDataSource = HogwartsRemoteDataSource(api)
    }

    @Test
    fun `getCharacters should return success`() = runTest {
        val mockResponse = mock<Response<GetCharactersResponse>> {
            on { isSuccessful } doReturn true
            on { body() } doReturn dummyGetCharactersResponse
        }
        whenever(api.getCharacters("", 1, 10)).thenReturn(
            mockResponse
        )

        val result = hogwartsRemoteDataSource.getCharacters("", 1, 10)

        verify(api).getCharacters("", 1, 10)
        verifyNoMoreInteractions(api)
        assertThat(result.data).isEqualTo(mockResponse.body())
        assertThat(result.status).isEqualTo(Status.SUCCESS)
    }

    @Test
    fun `getCharacters should return error`() = runTest {
        val mockResponse = mock<Response<GetCharactersResponse>> {
            on { isSuccessful } doReturn false
            on { errorBody() } doReturn null
        }
        whenever(api.getCharacters("", 1, 10)).thenReturn(mockResponse)

        val actualResult = hogwartsRemoteDataSource.getCharacters("", 1, 10)

        verify(api).getCharacters("", 1, 10)
        verifyNoMoreInteractions(api)
        assertThat(actualResult.data).isEqualTo(null)
        assertThat(actualResult.status).isEqualTo(Status.ERROR)
    }


    @Test
    fun `getCharactersByName should return success`() = runTest {
        val mockResponse = mock<Response<GetCharactersResponse>> {
            on { isSuccessful } doReturn true
            on { body() } doReturn dummyGetCharactersResponse
        }
        whenever(api.getCharactersByName("", 1, 10)).thenReturn(
            mockResponse
        )

        val result = hogwartsRemoteDataSource.getCharactersByName("", 1, 10)

        verify(api).getCharactersByName("", 1, 10)
        verifyNoMoreInteractions(api)
        assertThat(result.data).isEqualTo(mockResponse.body())
        assertThat(result.status).isEqualTo(Status.SUCCESS)
    }

    @Test
    fun `getCharactersByName should return error`() = runTest {
        val mockResponse = mock<Response<GetCharactersResponse>> {
            on { isSuccessful } doReturn false
            on { errorBody() } doReturn null
        }
        whenever(api.getCharactersByName("", 1, 10)).thenReturn(mockResponse)

        val result = hogwartsRemoteDataSource.getCharactersByName("", 1, 10)

        verify(api).getCharactersByName("", 1, 10)
        verifyNoMoreInteractions(api)
        assertThat(result.data).isEqualTo(null)
        assertThat(result.status).isEqualTo(Status.ERROR)
    }
}