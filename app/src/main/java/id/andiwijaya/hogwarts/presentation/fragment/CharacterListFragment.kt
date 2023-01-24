package id.andiwijaya.hogwarts.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.andiwijaya.hogwarts.core.Constants.ZERO
import id.andiwijaya.hogwarts.core.base.BaseFragment
import id.andiwijaya.hogwarts.databinding.FragmentCharacterListBinding
import id.andiwijaya.hogwarts.presentation.adapter.CharacterAdapter
import id.andiwijaya.hogwarts.presentation.adapter.CharacterLoadStateAdapter
import id.andiwijaya.hogwarts.presentation.viewmodel.CharacterListViewModel

@AndroidEntryPoint
class CharacterListFragment : BaseFragment<FragmentCharacterListBinding>() {

    private val viewModel by viewModels<CharacterListViewModel>()
    private val args: CharacterListFragmentArgs by navArgs()

    private val adapter by lazy {
        CharacterAdapter {}
    }

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentCharacterListBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(viewModel) {
        super.onViewCreated(view, savedInstanceState)

        processArgs(args)
        setupObserver()
        binding.rvCharacter.adapter = adapter.withLoadStateFooter(
            footer = CharacterLoadStateAdapter { getCharacters(house.value.orEmpty()) }
        )
        binding.rvCharacter.layoutManager = LinearLayoutManager(context)
        adapter.addLoadStateListener {
            binding.pbStory.isVisible = it.refresh is LoadState.Loading && adapter.itemCount == ZERO
            binding.cgError.isVisible = it.refresh is LoadState.Error && adapter.itemCount == ZERO
        }

        binding.ibReload.setOnClickListener { getCharacters(house.value.orEmpty()) }
    }

    private fun setupObserver() = with(viewModel) {
        house.observe(viewLifecycleOwner) {
            binding.tvTitle.text = it
            getCharacters(it)
        }
        character.observe(viewLifecycleOwner) {
            adapter.submitData(lifecycle, it)
        }
    }
}