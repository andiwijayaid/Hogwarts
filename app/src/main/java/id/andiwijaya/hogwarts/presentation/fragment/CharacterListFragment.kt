package id.andiwijaya.hogwarts.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.view.isInvisible
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
        CharacterAdapter {
            goTo(CharacterListFragmentDirections.actionToCharacterDetail(it))
        }
    }

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentCharacterListBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(viewModel) {
        super.onViewCreated(view, savedInstanceState)

        processArgs(args)
        setupHeader()
        setupObserver()
        setupList()
        setupAction()
    }

    private fun setupHeader() = with(binding) {
        etSearch.isVisible = viewModel.isSearch
        tvTitle.isInvisible = viewModel.isSearch
    }

    private fun setupList() = with(binding) {
        rvCharacter.adapter = adapter.withLoadStateFooter(
            footer = CharacterLoadStateAdapter {
                viewModel.getCharacters(
                    viewModel.keyword.value.orEmpty(), viewModel.isSearch
                )
            }
        )
        rvCharacter.layoutManager = LinearLayoutManager(context)
        adapter.addLoadStateListener {
            pbStory.isVisible = it.refresh is LoadState.Loading && adapter.itemCount == ZERO
            cgError.isVisible = it.refresh is LoadState.Error && adapter.itemCount == ZERO
        }
    }

    private fun setupAction() = with(binding) {
        ibReload.setOnClickListener { viewModel.getCharacters(viewModel.keyword.value.orEmpty()) }
        ibBackButton.setOnClickListener { back() }
        etSearch.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                viewModel.setKeyword(v.text.toString())
            }
            false
        }
    }

    private fun setupObserver() = with(viewModel) {
        keyword.observe(viewLifecycleOwner) {
            binding.tvTitle.text = it
            getCharacters(it, isSearch)
        }
        characters.observe(viewLifecycleOwner) {
            adapter.submitData(lifecycle, it)
        }
    }
}