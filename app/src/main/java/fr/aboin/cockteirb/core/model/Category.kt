package fr.aboin.cockteirb.core.model


import com.google.gson.annotations.SerializedName

/**
 * A object representing a cocktail category (when listing all categories)
 */
class Category {
    @SerializedName("strCategory")
    var name: String? = null
}

