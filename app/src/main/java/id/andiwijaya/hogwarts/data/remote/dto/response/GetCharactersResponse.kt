package id.andiwijaya.hogwarts.data.remote.dto.response

import com.google.gson.annotations.SerializedName
import id.andiwijaya.hogwarts.data.remote.dto.model.CharacterDto
import id.andiwijaya.hogwarts.domain.model.Character

data class GetCharactersResponse(
    @SerializedName("data")
    val characters: List<CharacterDto>
)

fun GetCharactersResponse.toCharacters() = characters.map {
    Character(
        id = it.id,
        bloodStatus = it.attributes.bloodStatus.orEmpty(),
        boggart = it.attributes.boggart.orEmpty(),
        born = it.attributes.born.orEmpty(),
        died = it.attributes.died.orEmpty(),
        eyeColor = it.attributes.eyeColor.orEmpty(),
        gender = it.attributes.gender.orEmpty(),
        hairColor = it.attributes.hairColor.orEmpty(),
        house = it.attributes.house.orEmpty(),
        image = it.attributes.image.orEmpty(),
        maritalStatus = it.attributes.maritalStatus.orEmpty(),
        name = it.attributes.name.orEmpty(),
        nationality = it.attributes.nationality.orEmpty(),
        species = it.attributes.species.orEmpty(),
        wiki = it.attributes.wiki.orEmpty()
    )
}