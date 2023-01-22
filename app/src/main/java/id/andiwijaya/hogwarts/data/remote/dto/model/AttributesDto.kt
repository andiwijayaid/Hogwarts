package id.andiwijaya.hogwarts.data.remote.dto.model


import com.google.gson.annotations.SerializedName

data class AttributesDto(
    @SerializedName("alias_names")
    val aliasNames: String? = null,
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
    @SerializedName("family_members")
    val familyMembers: String? = null,
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
    @SerializedName("jobs")
    val jobs: String? = null,
    @SerializedName("marital_status")
    val maritalStatus: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("nationality")
    val nationality: String? = null,
    @SerializedName("patronus")
    val patronus: String? = null,
    @SerializedName("romances")
    val romances: String? = null,
    @SerializedName("skin_color")
    val skinColor: String? = null,
    @SerializedName("slug")
    val slug: String? = null,
    @SerializedName("species")
    val species: String? = null,
    @SerializedName("titles")
    val titles: String? = null,
    @SerializedName("wands")
    val wands: String? = null,
    @SerializedName("weight")
    val weight: String? = null,
    @SerializedName("wiki")
    val wiki: String? = null
)