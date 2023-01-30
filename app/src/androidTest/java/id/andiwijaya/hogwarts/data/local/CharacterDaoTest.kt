package id.andiwijaya.hogwarts.data.local

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import id.andiwijaya.hogwarts.core.Constants.House.GRYFFINDOR
import id.andiwijaya.hogwarts.data.local.dao.CharacterDao
import id.andiwijaya.hogwarts.domain.model.Character
import id.andiwijaya.hogwarts.util.DataDummy
import id.andiwijaya.hogwarts.util.DataDummy.dummyCharacter
import id.andiwijaya.hogwarts.util.DataDummy.faker
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.test.assertEquals

@RunWith(AndroidJUnit4::class)
class CharacterDaoTest {

    private lateinit var characterDao: CharacterDao
    private lateinit var hogwartsDatabase: HogwartsDatabase
    private lateinit var dummyCharacters: MutableList<Character>

    @Before
    fun setup() {
        hogwartsDatabase = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().context, HogwartsDatabase::class.java
        ).build()
        characterDao = hogwartsDatabase.characterDao()
        dummyCharacters = DataDummy.dummyCharacters
    }

    @After
    fun tearDown() {
        hogwartsDatabase.clearAllTables()
        hogwartsDatabase.close()
    }

    @Test
    fun insertCharactersAndReturnTheSameNumberOfCharacters() = runBlocking {
        characterDao.insertCharacters(dummyCharacters)
        val expected = dummyCharacters.size
        val actual = characterDao.getNumberOfRecord()
        assertEquals(expected, actual)
    }

    @Test
    fun insertSameCharactersShouldReplaceTheValue() = runBlocking {
        val dummyCharacter = dummyCharacter()
        characterDao.insertCharacters(listOf(dummyCharacter, dummyCharacter))
        val expected = 1
        val actual = characterDao.getNumberOfRecord()
        assertEquals(expected, actual)
    }

    @Test
    fun getNumberOfCharactersByNameShouldReturnNumberOfItem() = runBlocking {
        val dummyCharacters = listOf(
            dummyCharacter().copy(name = faker.app.name().plus(1)),
            dummyCharacter().copy(name = faker.app.name().plus(2))
        )
        characterDao.insertCharacters(dummyCharacters)

        val expected = 1
        val firstCharacter = dummyCharacters.first()
        val actual = characterDao.getNumberOfCharactersByName(firstCharacter.name)
        assertEquals(expected, actual)
    }

    @Test
    fun getNumberOfCharactersByHouseShouldReturnNumberOfItem() = runBlocking {
        val dummyCharacters = dummyCharacters.map { it.copy(house = GRYFFINDOR) }
        characterDao.insertCharacters(dummyCharacters)

        val expected = dummyCharacters.size
        val actual = characterDao.getNumberOfCharactersByHouse(GRYFFINDOR)
        assertEquals(expected, actual)
    }

}