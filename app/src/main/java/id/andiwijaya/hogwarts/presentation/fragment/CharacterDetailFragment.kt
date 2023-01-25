package id.andiwijaya.hogwarts.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions.bitmapTransform
import dagger.hilt.android.AndroidEntryPoint
import id.andiwijaya.hogwarts.R
import id.andiwijaya.hogwarts.core.Constants.House.GRYFFINDOR
import id.andiwijaya.hogwarts.core.Constants.House.HUFFLEPUFF
import id.andiwijaya.hogwarts.core.Constants.House.RAVENCLAW
import id.andiwijaya.hogwarts.core.Constants.House.SLYTHERIN
import id.andiwijaya.hogwarts.core.base.BaseFragment
import id.andiwijaya.hogwarts.core.util.orHypen
import id.andiwijaya.hogwarts.databinding.FragmentCharacterDetailBinding
import id.andiwijaya.hogwarts.presentation.viewmodel.CharacterDetailViewModel
import jp.wasabeef.glide.transformations.BlurTransformation

@AndroidEntryPoint
class CharacterDetailFragment : BaseFragment<FragmentCharacterDetailBinding>() {

    private val viewModel by viewModels<CharacterDetailViewModel>()
    private val args: CharacterDetailFragmentArgs by navArgs()

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentCharacterDetailBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.processArgs(args)
        setupImage()
        tvName.text = viewModel.character?.name
        htvBorn.value = viewModel.character?.born.orHypen()
        htvDied.value = viewModel.character?.died.orHypen()
        htvNationality.value = viewModel.character?.nationality.orHypen()
        htvGender.value = viewModel.character?.gender.orHypen()
        htvHouse.value = viewModel.character?.house.orHypen()
        ibBackButton.setOnClickListener { back() }
    }

    private fun FragmentCharacterDetailBinding.setupImage() = context?.let {
        Glide.with(it)
            .load(
                when (viewModel.character?.house) {
                    GRYFFINDOR -> R.drawable.bg_gryffindor
                    SLYTHERIN -> R.drawable.bg_slytherin
                    RAVENCLAW -> R.drawable.bg_ravenclaw
                    HUFFLEPUFF -> R.drawable.bg_hufflepuff
                    else -> R.drawable.bg_detail_default
                }
            )
            .apply(bitmapTransform(BlurTransformation(2, 5)))
            .into(ivBackground)
        Glide.with(it)
            .load(viewModel.character?.image)
            .placeholder(R.drawable.ic_witch)
            .into(ivCharacter)
    }

}