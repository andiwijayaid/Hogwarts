package id.andiwijaya.hogwarts.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import id.andiwijaya.hogwarts.core.Constants.House.GRYFFINDOR
import id.andiwijaya.hogwarts.data.local.HogwartsDatabase
import id.andiwijaya.hogwarts.data.mediator.HogwartsRemoteMediator
import id.andiwijaya.hogwarts.domain.repository.HogwartsRepository
import id.andiwijaya.hogwarts.util.CharacterPagingSource
import id.andiwijaya.hogwarts.util.DataDummy.dummyCharacters
import id.andiwijaya.hogwarts.util.DataDummy.dummyGetCharactersResponse
import id.andiwijaya.hogwarts.util.MainDispatcherRule
import id.andiwijaya.hogwarts.util.PagingUtil.setupDiffer
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class HogwartsRepositoryImplTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val hogwartsDatabase = mock<HogwartsDatabase>()
    private val hogwartsRemoteMediator = mock<HogwartsRemoteMediator>()
    private val mockRepository = mock<HogwartsRepository>()

    private lateinit var hogwartsRepository: HogwartsRepositoryImpl

    @Before
    fun setup() {
        hogwartsRepository = HogwartsRepositoryImpl(hogwartsDatabase, hogwartsRemoteMediator)
    }

    @Test
    fun `get characters with pager successfully when isSearch false`() = runTest {
        val data = CharacterPagingSource.snapshot(dummyCharacters)
        val expectedResult = flowOf(data)
        whenever(mockRepository.getCharacters(GRYFFINDOR)).thenReturn(expectedResult)

        mockRepository.getCharacters(GRYFFINDOR).collect { result ->
            val differ = setupDiffer()
            differ.submitData(result)
            assertThat(differ.snapshot()).isNotNull()
            assertThat(dummyGetCharactersResponse.characters.size).isEqualTo(differ.snapshot().size)
        }
    }

    @Test
    fun `get characters with pager successfully when isSearch true`() = runTest {
        val data = CharacterPagingSource.snapshot(dummyCharacters)
        val expectedResult = flowOf(data)
        whenever(mockRepository.getCharacters(GRYFFINDOR, true)).thenReturn(expectedResult)

        mockRepository.getCharacters(GRYFFINDOR, true).collect { result ->
            val differ = setupDiffer()
            differ.submitData(result)
            assertThat(differ.snapshot()).isNotNull()
            assertThat(dummyGetCharactersResponse.characters.size).isEqualTo(differ.snapshot().size)
        }
    }
}