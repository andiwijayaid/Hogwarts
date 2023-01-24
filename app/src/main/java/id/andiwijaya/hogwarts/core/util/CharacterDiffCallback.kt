package id.andiwijaya.hogwarts.core.util

import androidx.recyclerview.widget.DiffUtil
import id.andiwijaya.hogwarts.domain.model.Character

object CharacterDiffCallback : DiffUtil.ItemCallback<Character>() {
    override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
        return oldItem == newItem
    }
}