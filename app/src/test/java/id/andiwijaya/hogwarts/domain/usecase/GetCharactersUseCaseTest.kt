package id.andiwijaya.hogwarts.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingData
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.whenever
import id.andiwijaya.hogwarts.core.Constants.EMPTY_STRING
import id.andiwijaya.hogwarts.data.repository.HogwartsRepositoryImpl
import id.andiwijaya.hogwarts.domain.model.Character
import id.andiwijaya.hogwarts.domain.usecase.impl.GetCharactersUseCaseImpl
import id.andiwijaya.hogwarts.util.CharacterPagingSource
import id.andiwijaya.hogwarts.util.DataDummy.dummyCharacters
import id.andiwijaya.hogwarts.util.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class GetCharactersUseCaseTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = MainDispatcherRule()

    private val repo = mock<HogwartsRepositoryImpl>()

    private lateinit var targetUseCase: GetCharactersUseCaseImpl

    @Before
    fun setup() {
        targetUseCase = GetCharactersUseCaseImpl(repo)
    }

    @Test
    fun `when invoke getCharacters`() = runTest {
        val expectedResult: PagingData<Character> = CharacterPagingSource.snapshot(dummyCharacters)
        whenever(repo.getCharacters(EMPTY_STRING)).thenReturn(flowOf(expectedResult))

        var result: PagingData<Character> = CharacterPagingSource.snapshot(listOf())
        targetUseCase(EMPTY_STRING).collect { result = it }

        verify(repo).getCharacters(EMPTY_STRING)
        verifyNoMoreInteractions(repo)
        assertThat(result).isEqualTo(expectedResult)
    }

}