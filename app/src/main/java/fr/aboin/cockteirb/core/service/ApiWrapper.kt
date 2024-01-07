package fr.aboin.cockteirb.core.service

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import fr.aboin.cockteirb.core.model.Category
import fr.aboin.cockteirb.core.model.Cocktail
import fr.aboin.cockteirb.core.model.CocktailDeserializer
import fr.aboin.cockteirb.core.model.CocktailSummary
import fr.aboin.cockteirb.core.model.Ingredient
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

data class ApiResponse<T>(
    @SerializedName("drinks")
    val drinks: List<T> = emptyList()
)

class ApiWrapper private constructor() {
    private val client = OkHttpClient()
    private val gson: Gson = GsonBuilder()
        .registerTypeAdapter(Cocktail::class.java, CocktailDeserializer())
        .create()

    companion object {
        val instance: ApiWrapper by lazy { ApiWrapper() }
        private const val baseUrl = "https://www.thecocktaildb.com/api/json/v1/1"
    }

    private var categories: List<Category>? = null
    private var ingredients: List<Ingredient>? = null
    private var cocktails: HashMap<String, Cocktail> = HashMap()
    private var cocktailsByCategory: HashMap<String, List<CocktailSummary>> = HashMap()

    private fun makeApiRequest(
        url: String,
        success: (String) -> Unit,
        failure: (Error) -> Unit
    ) {
        val request = Request.Builder().url(url).build()

        client.newCall(request).enqueue(object : okhttp3.Callback {
            override fun onFailure(call: Call, e: IOException) {
                failure(Error(e.localizedMessage))
            }

            override fun onResponse(call: Call, response: Response) {
                try {
                    val body = response.body?.string()
                    success(body.orEmpty())
                } catch (e: Exception) {
                    failure(Error(e.localizedMessage))
                }
            }
        })
    }

    private inline fun <reified T> parseApiResponse(json: String): List<T> {
        return gson.fromJson<ApiResponse<T>>(json, TypeToken.getParameterized(ApiResponse::class.java, T::class.java).type).drinks.orEmpty()
    }

    fun fetchCategories(
        success: (List<Category>) -> Unit,
        failure: (Error) -> Unit
    ) {
        if (categories != null) {
            success(categories!!)
        } else {
            val url = "$baseUrl/list.php?c=list"
            makeApiRequest(url, { body ->
                categories = parseApiResponse(body)
                success(categories!!)
            }, failure)
        }
    }

    fun fetchIngredients(
        success: (List<Ingredient>) -> Unit,
        failure: (Error) -> Unit
    ) {
        if (ingredients != null) {
            success(ingredients!!)
        } else {
            val url = "$baseUrl/list.php?i=list"
            makeApiRequest(url, { body ->
                ingredients = parseApiResponse(body)
                success(ingredients!!)
            }, failure)
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
            makeApiRequest(url, { body ->
                val cocktail = gson.fromJson(body, Cocktail::class.java)
                cocktails[id.toString()] = cocktail
                success(cocktail)
            }, failure)
        }
    }

    fun fetchCocktailsByCategory(
        category: String,
        success: (List<CocktailSummary>) -> Unit,
        failure: (Error) -> Unit
    ) {
        val cocktails = cocktailsByCategory[category]
        if (cocktails != null) {
            success(cocktails)
        } else {
            val url = "$baseUrl/filter.php?c=$category"
            makeApiRequest(url, { body ->
                val fetchedCocktails = parseApiResponse<CocktailSummary>(body)
                cocktailsByCategory[category] = fetchedCocktails
                success(fetchedCocktails)
            }, failure)
        }
    }
}
