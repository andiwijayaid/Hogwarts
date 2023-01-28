package id.andiwijaya.hogwarts.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.ListUpdateCallback
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import id.andiwijaya.hogwarts.core.util.CharacterDiffCallback
import id.andiwijaya.hogwarts.domain.model.Character
import id.andiwijaya.hogwarts.domain.model.CharacterListState
import id.andiwijaya.hogwarts.domain.usecase.GetCharactersUseCase
import id.andiwijaya.hogwarts.presentation.fragment.CharacterListFragmentArgs
import id.andiwijaya.hogwarts.util.CharacterPagingSource
import id.andiwijaya.hogwarts.util.DataDummy.dummyCharacters
import id.andiwijaya.hogwarts.util.DataDummy.faker
import id.andiwijaya.hogwarts.util.LiveDataTestUtils.getOrAwaitValue
import id.andiwijaya.hogwarts.util.MainDispatcherRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.random.Random

@ExperimentalCoroutinesApi
class CharacterListViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var targetViewModel: CharacterListViewModel
    private val getCharactersUseCase = mock<GetCharactersUseCase>()

    private val noopListUpdateCallback = object : ListUpdateCallback {
        override fun onInserted(position: Int, count: Int) {}
        override fun onRemoved(position: Int, count: Int) {}
        override fun onMoved(fromPosition: Int, toPosition: Int) {}
        override fun onChanged(position: Int, count: Int, payload: Any?) {}
    }

    @Before
    fun setUp() {
        targetViewModel = CharacterListViewModel(getCharactersUseCase)
    }

    @Test
    fun `when getCharacters should not null and return success`() = runTest {
        val dummyData: PagingData<Character> = CharacterPagingSource.snapshot(dummyCharacters)
        val fakeRequest = faker.app.name()
        whenever(getCharactersUseCase.invoke(fakeRequest)).thenReturn(flowOf(dummyData))

        targetViewModel.getCharacters(fakeRequest)
        val actualData: PagingData<Character> = targetViewModel.characters.getOrAwaitValue()

        val differ = AsyncPagingDataDiffer(
            diffCallback = CharacterDiffCallback,
            updateCallback = noopListUpdateCallback,
            workerDispatcher = Dispatchers.Main
        )
        differ.submitData(actualData)

        assertThat(differ.snapshot()).isNotNull()
        assertThat(dummyCharacters).isEqualTo(differ.snapshot())
        assertThat(dummyCharacters.size).isEqualTo(differ.snapshot().size)
    }

    @Test
    fun `updateListState receive loadState-refresh is LoadState-Loading`() {
        val loadState = mock<CombinedLoadStates> {
            on { refresh } doReturn LoadState.Loading
        }

        targetViewModel.updateListState(loadState, 0)

        val result = targetViewModel.listState.getOrAwaitValue()
        val expectedResult = CharacterListState.Loading

        assertThat(result).isEqualTo(expectedResult)
    }

    @Test
    fun `updateListState receive loadState-refresh is LoadState-Error`() {
        val loadState = mock<CombinedLoadStates> {
            on { refresh } doReturn mock<LoadState.Error>()
        }

        targetViewModel.updateListState(loadState, 0)

        val result = targetViewModel.listState.getOrAwaitValue()
        val expectedResult = CharacterListState.Error

        assertThat(result).isEqualTo(expectedResult)
    }

    @Test
    fun `updateListState receive endOfPaginationReached but adapter is zero`() {
        val mockNotLoading = mock<LoadState.NotLoading> {
            on { endOfPaginationReached } doReturn true
        }
        val loadState = mock<CombinedLoadStates> {
            on { append } doReturn mockNotLoading
        }

        targetViewModel.updateListState(loadState, 0)

        val result = targetViewModel.listState.getOrAwaitValue()
        val expectedResult = CharacterListState.NoDataFound

        assertThat(result).isEqualTo(expectedResult)
    }

    @Test
    fun `updateListState should be success on NotLoading, endOfPaginationReached, and itemCount is not zero`() {
        val mockNotLoading = mock<LoadState.NotLoading> {
            on { endOfPaginationReached } doReturn true
        }
        val loadState = mock<CombinedLoadStates> {
            on { append } doReturn mockNotLoading
        }

        targetViewModel.updateListState(loadState, Random.nextInt(1, 20))

        val result = targetViewModel.listState.getOrAwaitValue()
        val expectedResult = CharacterListState.Success

        assertThat(result).isEqualTo(expectedResult)
    }

    @Test
    fun `updateListState should be success on NotLoading, not endOfPaginationReached, and itemCount is not zero`() {
        val mockNotLoading = mock<LoadState.NotLoading> {
            on { endOfPaginationReached } doReturn false
        }
        val loadState = mock<CombinedLoadStates> {
            on { append } doReturn mockNotLoading
        }

        targetViewModel.updateListState(loadState, Random.nextInt(1, 20))

        val result = targetViewModel.listState.getOrAwaitValue()
        val expectedResult = CharacterListState.Success

        assertThat(result).isEqualTo(expectedResult)
    }

    @Test
    fun `when processArgs with isSearch false should set keyword`() {
        val args = CharacterListFragmentArgs(faker.app.name(), false)

        targetViewModel.processArgs(args)

        val actualHouse = targetViewModel.keyword.getOrAwaitValue()
        val actualIsSearch = targetViewModel.isSearch

        assertThat(args.houses).isEqualTo(actualHouse)
        assertThat(args.isSearch).isFalse()
        assertThat(args.isSearch).isEqualTo(actualIsSearch)
    }

    @Test
    fun `when processArgs with isSearch true should not set keyword`() {
        val args = CharacterListFragmentArgs(faker.app.name(), true)

        targetViewModel.processArgs(args)

        val actualIsSearch = targetViewModel.isSearch

        assertThat(targetViewModel.keyword.value).isNull()
        assertThat(args.isSearch).isTrue()
        assertThat(args.isSearch).isEqualTo(actualIsSearch)
    }

    @Test
    fun `setKeyword should set value to keyword`() {
        val dummyData = faker.app.name()
        targetViewModel.setKeyword(dummyData)
        val actualData = targetViewModel.keyword.getOrAwaitValue()

        assertThat(actualData).isNotNull()
        assertThat(dummyData).isEqualTo(actualData)
    }
}