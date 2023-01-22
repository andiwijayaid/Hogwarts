package id.andiwijaya.hogwarts.data.remote.dto.response

import com.google.gson.annotations.SerializedName
import id.andiwijaya.hogwarts.data.remote.dto.model.CharacterDto

data class GetCharactersResponse(
    @SerializedName("data")
    val characters: List<CharacterDto>
)