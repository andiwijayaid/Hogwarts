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
        val characterDto = dummyGetCharactersResponse.characters[randomInt]
        val character = characters[randomInt]

        assertThat(character.id).isEqualTo(characterDto.id)
        assertThat(character.bloodStatus).isEqualTo(characterDto.attributes.bloodStatus)
        assertThat(character.boggart).isEqualTo(characterDto.attributes.boggart)
        assertThat(character.born).isEqualTo(characterDto.attributes.born)
        assertThat(character.died).isEqualTo(characterDto.attributes.died)
        assertThat(character.eyeColor).isEqualTo(characterDto.attributes.eyeColor)
        assertThat(character.gender).isEqualTo(characterDto.attributes.gender)
        assertThat(character.hairColor).isEqualTo(characterDto.attributes.hairColor)
        assertThat(character.house).isEqualTo(characterDto.attributes.house)
        assertThat(character.image).isEqualTo(characterDto.attributes.image)
        assertThat(character.maritalStatus).isEqualTo(characterDto.attributes.maritalStatus)
        assertThat(character.name).isEqualTo(characterDto.attributes.name)
        assertThat(character.nationality).isEqualTo(characterDto.attributes.nationality)
        assertThat(character.species).isEqualTo(characterDto.attributes.species)
        assertThat(character.wiki).isEqualTo(characterDto.attributes.wiki)
    }

    @Test
    fun `toCharacters should return the same size`() {
        assertThat(dummyGetCharactersResponse.characters.size).isEqualTo(characters.size)
    }

}