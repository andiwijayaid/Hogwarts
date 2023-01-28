package id.andiwijaya.hogwarts.core.util

import com.google.common.truth.Truth.assertThat
import id.andiwijaya.hogwarts.util.DataDummy.dummyCharacter
import id.andiwijaya.hogwarts.util.DataDummy.faker
import org.junit.Test

class CharacterDiffCallbackTest {

    @Test
    fun `areItemsTheSame should return true`() {
        val result = CharacterDiffCallback.areItemsTheSame(dummyCharacter, dummyCharacter)
        assertThat(result).isTrue()
    }

    @Test
    fun `areItemsTheSame should return false`() {
        val result = CharacterDiffCallback.areItemsTheSame(
            dummyCharacter,
            dummyCharacter.copy(id = faker.app.name())
        )
        assertThat(result).isFalse()
    }

    @Test
    fun `areContentsTheSame should return true`() {
        val result = CharacterDiffCallback.areContentsTheSame(dummyCharacter, dummyCharacter)
        assertThat(result).isTrue()
    }

    @Test
    fun `areContentsTheSame should return false`() {
        val result = CharacterDiffCallback.areContentsTheSame(
            dummyCharacter,
            dummyCharacter.copy(name = faker.app.name())
        )
        assertThat(result).isFalse()
    }
}