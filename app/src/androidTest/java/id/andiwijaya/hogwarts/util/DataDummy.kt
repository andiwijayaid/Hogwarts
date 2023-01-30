package id.andiwijaya.hogwarts.util

import id.andiwijaya.hogwarts.data.remote.dto.model.AttributesDto
import id.andiwijaya.hogwarts.data.remote.dto.model.CharacterDto
import id.andiwijaya.hogwarts.data.remote.dto.response.GetCharactersResponse
import id.andiwijaya.hogwarts.domain.model.Character
import id.andiwijaya.hogwarts.domain.model.RemoteKeys
import io.github.serpro69.kfaker.Faker
import kotlin.random.Random

object DataDummy {

    val faker = Faker()

    fun dummyCharacter() = Character(
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

    val dummyCharacters = arrayListOf<Character>().apply {
        for (i in 0..10) {
            add(dummyCharacter())
        }
    }

    fun dummyRemoteKey() = RemoteKeys(faker.app.version(), Random.nextInt(), Random.nextInt())

    val dummyRemoteKeys = arrayListOf<RemoteKeys>().apply {
        for (i in 0..10) {
            add(dummyRemoteKey())
        }
    }

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

}