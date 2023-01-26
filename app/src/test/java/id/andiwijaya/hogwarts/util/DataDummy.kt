package id.andiwijaya.hogwarts.util

import id.andiwijaya.hogwarts.domain.model.Character
import io.github.serpro69.kfaker.Faker

object DataDummy {

    val faker = Faker()

    val dummyCharacter = Character(
        faker.app.version(),
        faker.app.version(),
        faker.app.version(),
        faker.app.version(),
        faker.app.version(),
        faker.app.version(),
        faker.app.version(),
        faker.app.version(),
        faker.app.version(),
        faker.app.version(),
        faker.app.version(),
        faker.app.version(),
        faker.app.version(),
        faker.app.version(),
        faker.app.version()
    )

    val dummyCharacters = arrayListOf<Character>().apply {
        for (i in 0..10) {
            add(dummyCharacter)
        }
    }

}