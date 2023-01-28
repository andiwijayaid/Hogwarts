package id.andiwijaya.hogwarts.data.remote.dto.response

import com.google.common.truth.Truth.assertThat
import id.andiwijaya.hogwarts.domain.model.Character
import id.andiwijaya.hogwarts.util.DataDummy.dummyGetCharactersResponse
import org.junit.Before
import org.junit.Test
import kotlin.random.Random

class GetCharactersResponseTest {

    private lateinit var characters: List<Character>

    @Before
    fun setup() {
        characters = dummyGetCharactersResponse.toCharacters()
    }

    @Test
    fun `toCharacters should map the correct value`() {
        val randomInt = Random.nextInt(0, dummyGetCharactersResponse.characters.size)
        val expectedResult = dummyGetCharactersResponse.characters[randomInt]
        val result = characters[randomInt]

        assertThat(result.id).isEqualTo(expectedResult.id)
        assertThat(result.bloodStatus).isEqualTo(expectedResult.attributes.bloodStatus)
        assertThat(result.boggart).isEqualTo(expectedResult.attributes.boggart)
        assertThat(result.born).isEqualTo(expectedResult.attributes.born)
        assertThat(result.died).isEqualTo(expectedResult.attributes.died)
        assertThat(result.eyeColor).isEqualTo(expectedResult.attributes.eyeColor)
        assertThat(result.gender).isEqualTo(expectedResult.attributes.gender)
        assertThat(result.hairColor).isEqualTo(expectedResult.attributes.hairColor)
        assertThat(result.house).isEqualTo(expectedResult.attributes.house)
        assertThat(result.image).isEqualTo(expectedResult.attributes.image)
        assertThat(result.maritalStatus).isEqualTo(expectedResult.attributes.maritalStatus)
        assertThat(result.name).isEqualTo(expectedResult.attributes.name)
        assertThat(result.nationality).isEqualTo(expectedResult.attributes.nationality)
        assertThat(result.species).isEqualTo(expectedResult.attributes.species)
        assertThat(result.wiki).isEqualTo(expectedResult.attributes.wiki)
    }

    @Test
    fun `toCharacters should return the same size`() {
        assertThat(dummyGetCharactersResponse.characters.size).isEqualTo(characters.size)
    }

}