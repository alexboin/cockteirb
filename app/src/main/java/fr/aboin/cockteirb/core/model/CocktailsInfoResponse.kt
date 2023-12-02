package fr.aboin.cockteirb.core.model


import com.google.gson.annotations.SerializedName

class CocktailsInfoResponse {
    @SerializedName("drinks")
    var cocktails: List<CocktailInfo> = emptyList()
}
