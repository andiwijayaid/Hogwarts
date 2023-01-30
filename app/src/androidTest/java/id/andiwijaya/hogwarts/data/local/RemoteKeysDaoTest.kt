package id.andiwijaya.hogwarts.data.local

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import id.andiwijaya.hogwarts.data.local.dao.RemoteKeysDao
import id.andiwijaya.hogwarts.util.DataDummy.dummyRemoteKey
import id.andiwijaya.hogwarts.util.DataDummy.dummyRemoteKeys
import id.andiwijaya.hogwarts.util.DataDummy.faker
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.test.assertEquals

@RunWith(AndroidJUnit4::class)
class RemoteKeysDaoTest {

    private lateinit var remoteKeysDao: RemoteKeysDao
    private lateinit var hogwartsDatabase: HogwartsDatabase


    @Before
    fun setup() {
        hogwartsDatabase = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().context, HogwartsDatabase::class.java
        ).build()
        remoteKeysDao = hogwartsDatabase.remoteKeysDao()

    }

    @After
    fun tearDown() {
        hogwartsDatabase.clearAllTables()
        hogwartsDatabase.close()
    }

    @Test
    fun insertCharactersAndReturnTheSameNumberOfCharacters() = runBlocking {
        remoteKeysDao.insertAll(dummyRemoteKeys)
        val expected = dummyRemoteKeys.size
        val actual = remoteKeysDao.getNumberOfRecord()
        assertEquals(expected, actual)
    }

    @Test
    fun insertSameCharactersShouldReplaceTheValue() = runBlocking {
        val dummyRemoteKey = dummyRemoteKey()
        remoteKeysDao.insertAll(listOf(dummyRemoteKey, dummyRemoteKey))
        val expected = 1
        val actual = remoteKeysDao.getNumberOfRecord()
        assertEquals(expected, actual)
    }

    @Test
    fun getNumberOfCharactersByNameShouldReturnNumberOfItem() = runBlocking {
        val dummyRemoteKeys = listOf(
            dummyRemoteKey().copy(id = faker.app.name().plus(1)),
            dummyRemoteKey().copy(id = faker.app.name().plus(2))
        )
        remoteKeysDao.insertAll(dummyRemoteKeys)

        val expected = dummyRemoteKeys.first()
        val actual = remoteKeysDao.getRemoteKeysId(expected.id)
        assertEquals(expected, actual)
    }
}