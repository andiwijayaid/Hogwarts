package id.andiwijaya.hogwarts.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import id.andiwijaya.hogwarts.core.base.BaseViewModel
import id.andiwijaya.hogwarts.domain.model.Character
import id.andiwijaya.hogwarts.domain.usecase.GetCharactersUseCase
import id.andiwijaya.hogwarts.presentation.fragment.CharacterListFragmentArgs
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase
) : BaseViewModel() {

    val house = MutableLiveData<String>()

    private val _characters = MutableLiveData<PagingData<Character>>()
    val characters: LiveData<PagingData<Character>> = _characters

    fun processArgs(args: CharacterListFragmentArgs) {
        house.value = args.houses
    }

    fun getCharacters(house: String) = collectFlow(
        getCharactersUseCase(house).cachedIn(viewModelScope), _characters
    )
}