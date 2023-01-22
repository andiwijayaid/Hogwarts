package id.andiwijaya.hogwarts.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import id.andiwijaya.hogwarts.core.base.BaseViewModel
import id.andiwijaya.hogwarts.presentation.fragment.CharacterListFragmentArgs
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor() : BaseViewModel() {

    val house = MutableLiveData<String>()

    fun processArgs(args: CharacterListFragmentArgs) {
        house.value = args.houses
    }


}