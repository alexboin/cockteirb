package fr.aboin.cockteirb.core.model

import com.google.gson.annotations.SerializedName

open class CocktailSummary {
    @SerializedName("idDrink")
    var id: String? = null
    @SerializedName("strDrink")
    var title: String? = null
    @SerializedName("strDrinkThumb")
    var imageURL: String? = null
}