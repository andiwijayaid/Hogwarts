package id.andiwijaya.hogwarts.util

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import id.andiwijaya.hogwarts.core.Constants.ONE
import id.andiwijaya.hogwarts.core.Constants.ZERO
import id.andiwijaya.hogwarts.domain.model.Character

class CharacterPagingSource : PagingSource<Int, LiveData<List<Character>>>() {
    companion object {
        fun snapshot(items: List<Character>): PagingData<Character> {
            return PagingData.from(items)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, LiveData<List<Character>>>) = ZERO

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LiveData<List<Character>>> {
        return LoadResult.Page(emptyList(), ZERO, ONE)
    }
}