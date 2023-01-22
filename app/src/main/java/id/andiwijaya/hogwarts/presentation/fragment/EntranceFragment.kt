package id.andiwijaya.hogwarts.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.hilt.android.AndroidEntryPoint
import id.andiwijaya.hogwarts.core.base.BaseFragment
import id.andiwijaya.hogwarts.databinding.FragmentEntranceBinding

@AndroidEntryPoint
class EntranceFragment : BaseFragment<FragmentEntranceBinding>() {

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentEntranceBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pop(EntranceFragmentDirections.actionToHouses())
    }

}