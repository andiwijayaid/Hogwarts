package id.andiwijaya.hogwarts.data.mediator

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import id.andiwijaya.hogwarts.data.local.HogwartsDatabase
import id.andiwijaya.hogwarts.data.remote.PotterDbApi
import id.andiwijaya.hogwarts.util.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import kotlin.random.Random

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class HogwartsRemoteMediatorTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val mockDatabase = mock<HogwartsDatabase>()
    private val mockApi = mock<PotterDbApi>()

    private lateinit var remoteMediator: HogwartsRemoteMediator

    @Before
    fun setup() = runTest {
        whenever(mockDatabase.characterDao()).thenReturn(mock())
        remoteMediator = HogwartsRemoteMediator(mockDatabase, mockApi)
    }

    @Test
    fun `isCharactersNotExistByHouse should return true when there is no record`() = runTest {
        whenever(mockDatabase.characterDao().getNumberOfCharactersByHouse("")).thenReturn(0)
        val result = remoteMediator.isCharactersNotExistByHouseTest("")
        assertThat(result).isTrue()
    }

    @Test
    fun `isCharactersNotExistByHouse should return false when there is record(s)`() = runTest {
        whenever(mockDatabase.characterDao().getNumberOfCharactersByHouse(""))
            .thenReturn(Random.nextInt(0, 100))
        val result = remoteMediator.isCharactersNotExistByHouseTest("")
        assertThat(result).isFalse()
    }

    @Test
    fun `isCharactersNotExistByName should return true when there is no record`() = runTest {
        whenever(mockDatabase.characterDao().getNumberOfCharactersByName("")).thenReturn(0)
        val result = remoteMediator.isCharactersNotExistByNameTest("")
        assertThat(result).isTrue()
    }

    @Test
    fun `isCharactersNotExistByName should return false when there is record(s)`() = runTest {
        whenever(mockDatabase.characterDao().getNumberOfCharactersByName(""))
            .thenReturn(Random.nextInt(0, 100))
        val result = remoteMediator.isCharactersNotExistByNameTest("")
        assertThat(result).isFalse()
    }

}