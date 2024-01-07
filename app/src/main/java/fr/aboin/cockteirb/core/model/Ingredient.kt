package fr.aboin.cockteirb.core.model

import com.google.gson.annotations.SerializedName

/**
 * A object representing a cocktail ingredient (when listing all ingredients)
 */
class Ingredient {
    @SerializedName("strIngredient1")
    var name: String? = null
}
