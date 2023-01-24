package id.andiwijaya.hogwarts.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.andiwijaya.hogwarts.R
import id.andiwijaya.hogwarts.core.util.CharacterDiffCallback
import id.andiwijaya.hogwarts.databinding.ItemCharacterBinding
import id.andiwijaya.hogwarts.domain.model.Character

class CharacterAdapter(
    private val onItemSelected: () -> Unit
) : PagingDataAdapter<Character, CharacterAdapter.ViewHolder>(CharacterDiffCallback) {

    class ViewHolder(
        private val binding: ItemCharacterBinding,
        val onItemSelected: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(character: Character) = with(binding) {

            Glide.with(root.context)
                .load(character.image)
                .placeholder(R.drawable.ic_witch)
                .into(ivCharacter)
            tvName.text = character.name
            tvSpecies.text = character.species

        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        onItemSelected
    )

}