package id.andiwijaya.hogwarts.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "character")
data class Character(
    @PrimaryKey
    val id: String,
    val bloodStatus: String,
    val boggart: String,
    val born: String,
    val died: String,
    val eyeColor: String,
    val gender: String,
    val hairColor: String,
    val house: String,
    val image: String,
    val maritalStatus: String,
    val name: String,
    val nationality: String,
    val species: String,
    val wiki: String
) : Parcelable