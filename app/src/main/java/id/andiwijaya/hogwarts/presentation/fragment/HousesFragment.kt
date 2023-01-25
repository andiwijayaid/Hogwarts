package id.andiwijaya.hogwarts.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.hilt.android.AndroidEntryPoint
import id.andiwijaya.hogwarts.core.Constants.EMPTY_STRING
import id.andiwijaya.hogwarts.core.Constants.House.GRYFFINDOR
import id.andiwijaya.hogwarts.core.Constants.House.HUFFLEPUFF
import id.andiwijaya.hogwarts.core.Constants.House.RAVENCLAW
import id.andiwijaya.hogwarts.core.Constants.House.SLYTHERIN
import id.andiwijaya.hogwarts.core.base.BaseFragment
import id.andiwijaya.hogwarts.databinding.FragmentHousesBinding

@AndroidEntryPoint
class HousesFragment : BaseFragment<FragmentHousesBinding>() {

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentHousesBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)
        btGryffindor.setOnClickListener { navigateToCharacterList(GRYFFINDOR) }
        btSlytherin.setOnClickListener { navigateToCharacterList(SLYTHERIN) }
        btRavenclaw.setOnClickListener { navigateToCharacterList(RAVENCLAW) }
        btHufflepuff.setOnClickListener { navigateToCharacterList(HUFFLEPUFF) }
        btSearch.setOnClickListener { navigateToCharacterList(EMPTY_STRING) }
    }

    private fun navigateToCharacterList(house: String) {
        goTo(HousesFragmentDirections.actionToCharacterList(house))
    }

}