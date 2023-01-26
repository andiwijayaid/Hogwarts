package id.andiwijaya.hogwarts.core.util

import com.google.common.truth.Truth.assertThat
import id.andiwijaya.hogwarts.util.DataDummy.dummyCharacter
import id.andiwijaya.hogwarts.util.DataDummy.faker
import org.junit.Test

class CharacterDiffCallbackTest {

    @Test
    fun `areItemsTheSame should return true`() {
        val actualResult = CharacterDiffCallback.areItemsTheSame(dummyCharacter, dummyCharacter)
        assertThat(actualResult).isTrue()
    }

    @Test
    fun `areItemsTheSame should return false`() {
        val actualResult = CharacterDiffCallback.areItemsTheSame(
            dummyCharacter,
            dummyCharacter.copy(id = faker.app.name())
        )
        assertThat(actualResult).isFalse()
    }

    @Test
    fun `areContentsTheSame should return true`() {
        val actualResult = CharacterDiffCallback.areContentsTheSame(dummyCharacter, dummyCharacter)
        assertThat(actualResult).isTrue()
    }

    @Test
    fun `areContentsTheSame should return false`() {
        val actualResult = CharacterDiffCallback.areContentsTheSame(
            dummyCharacter,
            dummyCharacter.copy(name = faker.app.name())
        )
        assertThat(actualResult).isFalse()
    }
}