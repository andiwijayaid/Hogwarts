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

    private val _keyword = MutableLiveData<String>()
    val keyword: LiveData<String> = _keyword

    var isSearch = false
        private set

    private val _characters = MutableLiveData<PagingData<Character>>()
    val characters: LiveData<PagingData<Character>> = _characters

    fun processArgs(args: CharacterListFragmentArgs) {
        isSearch = args.isSearch
        if (isSearch.not()) setKeyword(args.houses)
    }

    fun setKeyword(keyword: String) {
        _keyword.value = keyword
    }

    fun getCharacters(keyword: String, isSearch: Boolean = false) = collectFlow(
        getCharactersUseCase(keyword, isSearch).cachedIn(viewModelScope), _characters
    )
}