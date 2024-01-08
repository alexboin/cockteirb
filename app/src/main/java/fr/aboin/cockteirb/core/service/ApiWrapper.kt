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

/**
 * A object representing a response from TheCocktailDB API
 */
data class ApiResponse<T>(
    @SerializedName("drinks")
    val drinks: List<T> = emptyList()
)

/**
 * A wrapper around TheCocktailDB API
 */
class ApiWrapper private constructor() {
    private val client = OkHttpClient()
    private val gson: Gson = GsonBuilder()
        .registerTypeAdapter(Cocktail::class.java, CocktailDeserializer())
        .create()

    // Singleton
    companion object {
        val instance: ApiWrapper by lazy { ApiWrapper() }
        private const val baseUrl = "https://www.thecocktaildb.com/api/json/v1/1"
    }

    // Cache
    private var categories: List<Category>? = null
    private var ingredients: List<Ingredient>? = null
    private var cocktails: HashMap<String, Cocktail> = HashMap()
    private var cocktailsByCategory: HashMap<String, List<CocktailSummary>> = HashMap()

    /**
     * Make a request to the API
     * @param url The URL to request
     * @param success The callback to call when the request is successful
     * @param failure The callback to call when the request fails
     */
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

    /**
     * Parse the response from the API into a list of objects from the desired type
     * @param json The JSON to parse
     * @return The parsed response
     */
    private inline fun <reified T> parseApiResponse(json: String): List<T> {
        return gson.fromJson<ApiResponse<T>>(json, TypeToken.getParameterized(ApiResponse::class.java, T::class.java).type).drinks.orEmpty()
    }

    /**
     * Fetch all the categories of cocktails from the API
     * @param success The callback to call when the request is successful (with the list of categories)
     * @param failure The callback to call when the request fails
     */
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

    /**
     * Fetch all the ingredients from the API
     * @param success The callback to call when the request is successful (with the list of ingredients)
     * @param failure The callback to call when the request fails
     */
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

    /**
     * Fetch the details of a cocktail from the API
     * @param id The id of the cocktail to fetch
     * @param success The callback to call when the request is successful (with the cocktail details)
     * @param failure The callback to call when the request fails
     */
    fun fetchCocktailDetails(
        id: String,
        success: (Cocktail) -> Unit,
        failure: (Error) -> Unit
    ) {
        val cocktail = cocktails[id]
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

    /**
     * Fetch all the cocktails of a category from the API
     * @param category The category of the cocktails to fetch
     * @param success The callback to call when the request is successful (with the list of cocktails)
     * @param failure The callback to call when the request fails
     */
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

    /**
     * Fetch all the cocktails matching a name from the API
     * @param name The name of the cocktails to fetch
     * @param success The callback to call when the request is successful (with the list of cocktails)
     * @param failure The callback to call when the request fails
     */
    fun fetchCocktailsByName(
        name: String,
        success: (List<CocktailSummary>) -> Unit,
        failure: (Error) -> Unit
    ) {
        val url = "$baseUrl/search.php?s=$name"
        makeApiRequest(url, { body ->
            val fetchedCocktails = parseApiResponse<CocktailSummary>(body)
            success(fetchedCocktails)
        }, failure)
    }

    /**
     * Fetch all the cocktails containing an ingredient from the API
     * @param ingredient The ingredient of the cocktails to fetch
     * @param success The callback to call when the request is successful (with the list of cocktails)
     * @param failure The callback to call when the request fails
     */
    fun fetchCocktailsByIngredient(
        ingredient: String,
        success: (List<CocktailSummary>) -> Unit,
        failure: (Error) -> Unit
    ) {
        val url = "$baseUrl/filter.php?i=$ingredient"
        makeApiRequest(url, { body ->
            val fetchedCocktails = parseApiResponse<CocktailSummary>(body)
            success(fetchedCocktails)
        }, failure)
    }
}
