package id.andiwijaya.hogwarts.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.andiwijaya.hogwarts.domain.model.Character

@Dao
interface CharacterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacters(characters: List<Character>)

    @Query("SELECT * FROM character WHERE house is :houseName ORDER BY name ASC")
    fun getCharacters(houseName: String): PagingSource<Int, Character>

    @Query("SELECT COUNT(*) FROM character WHERE house is :houseName")
    suspend fun getNumberOfCharacters(houseName: String): Int
}