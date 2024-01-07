package fr.aboin.cockteirb.core.service

import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import fr.aboin.cockteirb.core.model.Category
import fr.aboin.cockteirb.core.model.Cocktail
import fr.aboin.cockteirb.core.model.CocktailDeserializer
import fr.aboin.cockteirb.core.model.CocktailSummary
import fr.aboin.cockteirb.core.model.Ingredient
import okhttp3.OkHttpClient
import java.io.IOException

data class ApiResponse<T>(
    @SerializedName("drinks")
    val drinks: List<T> = emptyList()
)

class ApiWrapper {
    companion object {
        private val client = OkHttpClient()
        private const val baseUrl = "https://www.thecocktaildb.com/api/json/v1/1"

        // Singleton
        private var instance: ApiWrapper? = null

        fun getInstance(): ApiWrapper {
            if (instance == null) {
                instance = ApiWrapper()
            }
            return instance!!
        }
    }

    // Cache
    private var categories: List<Category>? = null
    private var ingredients: List<Ingredient>? = null
    private var cocktails: HashMap<String, Cocktail> = HashMap()
    private var cocktailsByCategory: HashMap<String, List<CocktailSummary>> = HashMap()

    fun fetchCategories(
        success: (List<Category>) -> Unit,
        failure: (Error) -> Unit
    ) {
        // Si cache
        if (categories != null) {
            success(categories!!)
            return
        } else {
            val url = "$baseUrl/list.php?c=list"
            var request = okhttp3.Request.Builder().url(url).build()

            client
                .newCall(request)
                .enqueue(object : okhttp3.Callback {
                    override fun onFailure(call: okhttp3.Call, e: java.io.IOException) {
                        failure(Error(e.localizedMessage))
                    }

                    override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                        val gson = com.google.gson.Gson()
                        val body = response.body?.string()
                        val categoriesResponse = gson.fromJson<ApiResponse<Category>>(body, TypeToken.getParameterized(
                            ApiResponse::class.java, Category::class.java).type)
                        categories = categoriesResponse.drinks
                        success(categoriesResponse.drinks)
                    }
                })

        }
    }

    fun fetchIngredients(
        success: (List<Ingredient>) -> Unit,
        failure: (Error) -> Unit
    ) {
        // Si cache
        if (ingredients != null) {
            success(ingredients!!)
            return
        } else {
            val url = "$baseUrl/list.php?i=list"
            var request = okhttp3.Request.Builder().url(url).build()

            client
                .newCall(request)
                .enqueue(object : okhttp3.Callback {
                    override fun onFailure(call: okhttp3.Call, e: IOException) {
                        failure(Error(e.localizedMessage))
                    }

                    override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                        try {
                            val gson = com.google.gson.Gson()
                            val body = response.body?.string()
                            val ingredientsResponse = gson.fromJson<ApiResponse<Ingredient>>(body, TypeToken.getParameterized(
                                ApiResponse::class.java, Ingredient::class.java).type)
                            ingredients = ingredientsResponse.drinks
                            success(ingredientsResponse.drinks)
                        } catch (e: Exception) {
                            failure(Error(e.localizedMessage))
                        }
                    }
                })
        }
    }


    fun fetchCocktailDetails(
        id: Int,
        success: (Cocktail) -> Unit,
        failure: (Error) -> Unit
    ) {
        val cocktail = cocktails[id.toString()]
        if (cocktail != null) {
            success(cocktail)
        } else {
            val url = "$baseUrl/lookup.php?i=$id"
            var request = okhttp3.Request.Builder().url(url).build()

            client
                .newCall(request)
                .enqueue(object : okhttp3.Callback {
                    override fun onFailure(call: okhttp3.Call, e: java.io.IOException) {
                        failure(Error(e.localizedMessage))
                    }

                    override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                        val gSon = GsonBuilder().registerTypeAdapter(Cocktail::class.java, CocktailDeserializer()).create()
                        val body = response.body?.string()
                        try {
                            val cocktail = gSon.fromJson(body, Cocktail::class.java)
                            cocktails[id.toString()] = cocktail
                            success(cocktail)
                        } catch (e: Exception) {
                            failure(Error(e.localizedMessage))
                        }
                    }
                })
        }
    }
// TODO gerer le cas ou pas de connexion internet
    fun fetchCocktailsByCategory(
    category: String,
    success: (List<CocktailSummary>) -> Unit,
    failure: (Error) -> Unit
    ) {
        val cocktails = cocktailsByCategory[category]
        if (cocktails != null) {
            // If in cache, return the list
            success(cocktails)
        } else {
            // Make an OKHTTP call
            val url = "$baseUrl/filter.php?c=$category"
            val request = okhttp3.Request.Builder().url(url).build()

            val gson = GsonBuilder().create()

            client.newCall(request).enqueue(object : okhttp3.Callback {
                override fun onFailure(call: okhttp3.Call, e: IOException) {
                    failure(Error(e.localizedMessage))
                }

                override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                    // If success
                    try {
                        val body = response.body?.string()

                        val cocktailsResponse = gson.fromJson<ApiResponse<CocktailSummary>>(body, TypeToken.getParameterized(
                            ApiResponse::class.java, CocktailSummary::class.java).type)
                        val fetchedCocktails = cocktailsResponse.drinks
                        if (fetchedCocktails != null) {
                            // Update cocktailsByCategory to use CocktailInfo
                            cocktailsByCategory[category] = fetchedCocktails
                            success(fetchedCocktails)
                        } else {
                            failure(Error("No cocktails found for the category: $category"))
                        }
                    } catch (e: Exception) {
                        failure(Error(e.localizedMessage))
                    }
                }
            })
        }
    }
}