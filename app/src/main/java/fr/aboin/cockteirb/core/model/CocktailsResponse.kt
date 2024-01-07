package fr.aboin.cockteirb.core.model

import com.google.gson.annotations.SerializedName

class CocktailsResponse {
    @SerializedName("drinks")
    val cocktails: List<Cocktail>? = null
}
