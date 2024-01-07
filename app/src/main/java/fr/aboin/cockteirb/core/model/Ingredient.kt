package fr.aboin.cockteirb.core.model

import com.google.gson.annotations.SerializedName

data class Ingredient(
    @SerializedName("strIngredient1")
    val name: String
)
