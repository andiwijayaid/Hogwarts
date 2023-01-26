package id.andiwijaya.hogwarts.presentation.viewmodel

import com.google.common.truth.Truth.assertThat
import id.andiwijaya.hogwarts.presentation.fragment.CharacterDetailFragmentArgs
import id.andiwijaya.hogwarts.util.DataDummy.dummyCharacter
import org.junit.Before
import org.junit.Test

class CharacterDetailViewModelTest {

    private lateinit var targetViewModel: CharacterDetailViewModel

    @Before
    fun setUp() {
        targetViewModel = CharacterDetailViewModel()
    }

    @Test
    fun `character should be null when there is processArgs`() {
        assertThat(targetViewModel.character).isNull()
    }

    @Test
    fun `processArgs should set Character from arguments`() {
        targetViewModel.processArgs(CharacterDetailFragmentArgs(dummyCharacter))
        assertThat(targetViewModel.character).isNotNull()
    }
}