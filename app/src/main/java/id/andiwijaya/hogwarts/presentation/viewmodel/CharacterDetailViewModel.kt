package id.andiwijaya.hogwarts.presentation.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import id.andiwijaya.hogwarts.core.base.BaseViewModel
import id.andiwijaya.hogwarts.domain.model.Character
import id.andiwijaya.hogwarts.presentation.fragment.CharacterDetailFragmentArgs
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor() : BaseViewModel() {

    var character: Character? = null
        private set

    fun processArgs(args: CharacterDetailFragmentArgs) {
        character = args.character
    }
}