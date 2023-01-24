package id.andiwijaya.hogwarts.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import id.andiwijaya.hogwarts.core.Constants.Database.DB_NAME
import id.andiwijaya.hogwarts.core.Constants.Database.DB_VERSION
import id.andiwijaya.hogwarts.data.local.dao.CharacterDao
import id.andiwijaya.hogwarts.data.local.dao.RemoteKeysDao
import id.andiwijaya.hogwarts.domain.model.Character
import id.andiwijaya.hogwarts.domain.model.RemoteKeys

@Database(
    entities = [Character::class, RemoteKeys::class],
    version = DB_VERSION,
    exportSchema = false
)
abstract class HogwartsDatabase : RoomDatabase() {

    abstract fun characterDao(): CharacterDao
    abstract fun remoteKeysDao(): RemoteKeysDao

    companion object {
        @Volatile
        private var INSTANCE: HogwartsDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): HogwartsDatabase {
            return INSTANCE ?: Room.databaseBuilder(
                context.applicationContext,
                HogwartsDatabase::class.java,
                DB_NAME
            ).fallbackToDestructiveMigration().build().also { INSTANCE = it }
        }
    }

}