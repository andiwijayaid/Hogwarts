package id.andiwijaya.hogwarts.data.remote.dto.model


import com.google.gson.annotations.SerializedName

data class CharacterDto(
    @SerializedName("attributes")
    val attributes: AttributesDto,
    @SerializedName("id")
    val id: String,
    @SerializedName("type")
    val type: String
)