package com.example.infoPokemon

import com.google.gson.annotations.SerializedName


data class Icons(

    @SerializedName("front_default") var frontDefault: String? = null,
    @SerializedName("front_female") var frontFemale: String? = null

)