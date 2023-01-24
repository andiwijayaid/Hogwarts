package id.andiwijaya.hogwarts.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import id.andiwijaya.hogwarts.databinding.ItemCharacterLoadFooterBinding

class CharacterLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<CharacterLoadStateAdapter.ViewHolder>() {

    class ViewHolder(
        private val binding: ItemCharacterLoadFooterBinding,
        retry: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.ibReload.setOnClickListener { retry() }
        }

        fun bind(loadState: LoadState) = with(binding) {
            pbStory.isVisible = loadState is LoadState.Loading || loadState !is LoadState.NotLoading
            ibReload.isVisible = loadState is LoadState.Error
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState) = ViewHolder(
        ItemCharacterLoadFooterBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        retry
    )
}