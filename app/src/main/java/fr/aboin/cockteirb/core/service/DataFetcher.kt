package fr.aboin.cockteirb.core.service

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import fr.aboin.cockteirb.core.model.CategoriesResponse
import fr.aboin.cockteirb.core.model.Category
import fr.aboin.cockteirb.core.model.Cocktail
import fr.aboin.cockteirb.core.model.CocktailDeserializer
import fr.aboin.cockteirb.core.model.CocktailInfo
import fr.aboin.cockteirb.core.model.CocktailInfoDeserializer
import fr.aboin.cockteirb.core.model.CocktailsInfoResponse
import fr.aboin.cockteirb.core.model.CocktailsResponse
import fr.aboin.cockteirb.core.model.Ingredient
import fr.aboin.cockteirb.core.model.IngredientsResponse
import fr.aboin.cockteirb.core.model.SimpleCocktailDeserializer
import okhttp3.OkHttpClient
import java.io.IOException

class DataFetcher {
    companion object {
        private val client = OkHttpClient()
        private const val baseUrl = "https://www.thecocktaildb.com/api/json/v1/1"

        // Singleton
        private var instance: DataFetcher? = null

        fun getInstance(): DataFetcher {
            if (instance == null) {
                instance = DataFetcher()
            }
            return instance!!
        }
    }

    // Cache
    private var categories: List<Category>? = null
    private var ingredients: List<Ingredient>? = null
    private var cocktails: HashMap<String, Cocktail> = HashMap()
    private var cocktailsByCategory: HashMap<String, List<CocktailInfo>> = HashMap()
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
                        val categoriesResponse = gson.fromJson(body, CategoriesResponse::class.java)
                        categories = categoriesResponse.categories
                        success(categoriesResponse.categories)
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
                            val ingredientsResponse = gson.fromJson(body, IngredientsResponse::class.java)
                            ingredients = ingredientsResponse.ingredients
                            success(ingredientsResponse.ingredients)
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
        success: (List<CocktailInfo>) -> Unit,
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

            val gson = GsonBuilder()
                .registerTypeAdapter(CocktailInfo::class.java, CocktailInfoDeserializer())
                .create()

            client.newCall(request).enqueue(object : okhttp3.Callback {
                override fun onFailure(call: okhttp3.Call, e: IOException) {
                    failure(Error(e.localizedMessage))
                }

                override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                    // If success
                    try {
                        val body = response.body?.string()
                        val cocktailsInfoResponse = gson.fromJson(body, CocktailsInfoResponse::class.java)
                        val fetchedCocktails = cocktailsInfoResponse.cocktails
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