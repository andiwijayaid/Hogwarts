package id.andiwijaya.hogwarts.presentation.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import dagger.hilt.android.AndroidEntryPoint
import id.andiwijaya.hogwarts.core.base.BaseFragment
import id.andiwijaya.hogwarts.databinding.FragmentHousesBinding

@AndroidEntryPoint
class HousesFragment : BaseFragment<FragmentHousesBinding>() {

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentHousesBinding.inflate(inflater, container, false)

}