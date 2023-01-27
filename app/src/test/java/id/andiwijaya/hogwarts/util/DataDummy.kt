package id.andiwijaya.hogwarts.util

import id.andiwijaya.hogwarts.data.remote.dto.model.AttributesDto
import id.andiwijaya.hogwarts.data.remote.dto.model.CharacterDto
import id.andiwijaya.hogwarts.data.remote.dto.response.GetCharactersResponse
import id.andiwijaya.hogwarts.domain.model.Character
import io.github.serpro69.kfaker.Faker

object DataDummy {

    val faker = Faker()

    val dummyCharacter = Character(
        id = faker.app.version(),
        bloodStatus = faker.app.version(),
        boggart = faker.app.version(),
        born = faker.app.version(),
        died = faker.app.version(),
        eyeColor = faker.app.version(),
        gender = faker.app.version(),
        hairColor = faker.app.version(),
        house = faker.app.version(),
        image = faker.app.version(),
        maritalStatus = faker.app.version(),
        name = faker.app.version(),
        nationality = faker.app.version(),
        species = faker.app.version(),
        wiki = faker.app.version()
    )

    private val dummyListOfString = arrayListOf<String>().apply {
        for (i in 0..10) {
            add(faker.app.version())
        }
    }

    private val dummyAttributesDto = AttributesDto(
        animagus = faker.app.version(),
        bloodStatus = faker.app.version(),
        boggart = faker.app.version(),
        born = faker.app.version(),
        died = faker.app.version(),
        eyeColor = faker.app.version(),
        gender = faker.app.version(),
        hairColor = faker.app.version(),
        height = faker.app.version(),
        house = faker.app.version(),
        image = faker.app.version(),
        maritalStatus = faker.app.version(),
        name = faker.app.version(),
        nationality = faker.app.version(),
        patronus = faker.app.version(),
        skinColor = faker.app.version(),
        slug = faker.app.version(),
        weight = faker.app.version(),
        wiki = faker.app.version(),
        species = faker.app.version(),
        aliasNames = dummyListOfString,
        familyMembers = dummyListOfString,
        jobs = dummyListOfString,
        romances = dummyListOfString,
        titles = dummyListOfString,
        wands = dummyListOfString
    )

    private val dummyCharacterDto = CharacterDto(
        attributes = dummyAttributesDto,
        id = faker.app.version(),
        type = faker.app.version()
    )

    val dummyGetCharactersResponse = GetCharactersResponse(
        arrayListOf<CharacterDto>().apply {
            for (i in 0..10) {
                add(dummyCharacterDto)
            }
        }
    )

    val dummyCharacters = arrayListOf<Character>().apply {
        for (i in 0..10) {
            add(dummyCharacter)
        }
    }

}