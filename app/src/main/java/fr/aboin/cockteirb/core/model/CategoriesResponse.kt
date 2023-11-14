package fr.aboin.cockteirb.core.model

import com.google.gson.annotations.SerializedName

class CategoriesResponse {
    @SerializedName("drinks")
    var categories: List<Category> = emptyList()
}