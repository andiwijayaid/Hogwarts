package id.andiwijaya.hogwarts.util

import androidx.paging.AsyncPagingDataDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
import id.andiwijaya.hogwarts.domain.model.Character
import kotlinx.coroutines.Dispatchers

object PagingUtil {
    class DiffCallback : DiffUtil.ItemCallback<Character>() {
        override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean =
            oldItem == newItem
    }

    class NoopListCallback : ListUpdateCallback {
        override fun onInserted(position: Int, count: Int) {}
        override fun onRemoved(position: Int, count: Int) {}
        override fun onMoved(fromPosition: Int, toPosition: Int) {}
        override fun onChanged(position: Int, count: Int, payload: Any?) {}
    }

    fun setupDiffer() = AsyncPagingDataDiffer(
        diffCallback = DiffCallback(),
        updateCallback = NoopListCallback(),
        mainDispatcher = Dispatchers.Main,
        workerDispatcher = Dispatchers.Main
    )
}