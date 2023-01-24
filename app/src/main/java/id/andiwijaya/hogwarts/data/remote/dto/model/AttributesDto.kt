package id.andiwijaya.hogwarts.data.remote.dto.model


import com.google.gson.annotations.SerializedName

data class AttributesDto(
    @SerializedName("animagus")
    val animagus: String? = null,
    @SerializedName("blood_status")
    val bloodStatus: String? = null,
    @SerializedName("boggart")
    val boggart: String? = null,
    @SerializedName("born")
    val born: String? = null,
    @SerializedName("died")
    val died: String? = null,
    @SerializedName("eye_color")
    val eyeColor: String? = null,
    @SerializedName("gender")
    val gender: String? = null,
    @SerializedName("hair_color")
    val hairColor: String? = null,
    @SerializedName("height")
    val height: String? = null,
    @SerializedName("house")
    val house: String? = null,
    @SerializedName("image")
    val image: String? = null,
    @SerializedName("marital_status")
    val maritalStatus: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("nationality")
    val nationality: String? = null,
    @SerializedName("patronus")
    val patronus: String? = null,
    @SerializedName("skin_color")
    val skinColor: String? = null,
    @SerializedName("slug")
    val slug: String? = null,
    @SerializedName("weight")
    val weight: String? = null,
    @SerializedName("wiki")
    val wiki: String? = null,
    @SerializedName("species")
    val species: String? = null,
    @SerializedName("alias_names")
    val aliasNames: List<String>? = listOf(),
    @SerializedName("family_members")
    val familyMembers: List<String>? = listOf(),
    @SerializedName("jobs")
    val jobs: List<String>? = listOf(),
    @SerializedName("romances")
    val romances: List<String>? = listOf(),
    @SerializedName("titles")
    val titles: List<String>? = listOf(),
    @SerializedName("wands")
    val wands: List<String>? = listOf()
)