package fr.aboin.cockteirb.core.service

import fr.aboin.cockteirb.core.model.CategoriesResponse
import fr.aboin.cockteirb.core.model.Category
import okhttp3.OkHttpClient

class CategoriesFetcher {
    companion object {
        private val client = OkHttpClient()
        private const val baseUrl = "https://www.thecocktaildb.com/api/json/v1/1"

        // Singleton
        private var instance: CategoriesFetcher? = null

        fun getInstance(): CategoriesFetcher {
            if (instance == null) {
                instance = CategoriesFetcher()
            }
            return instance!!
        }

    }

    // Cache
    private var categories: List<Category>? = null

    fun fetchCategories(
        success: (List<Category>) -> Unit,
        failure: (Error) -> Unit
    ) {
        // Si cache
        if (categories != null) {
            success(categories!!)
            return
        } else {
            // Appel à OKHTTP
            val url = "$baseUrl/list.php?c=list"
            var request = okhttp3.Request.Builder().url(url).build()

            client
                .newCall(request)
                .enqueue(object : okhttp3.Callback {
                    override fun onFailure(call: okhttp3.Call, e: java.io.IOException) {
                        // Si erreur
                        failure(Error(e.localizedMessage))
                    }

                    override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                        // Si succès
                        val gson = com.google.gson.Gson()
                        val body = response.body?.string()
                        val categoriesResponse = gson.fromJson(body, CategoriesResponse::class.java)
                        categories = categoriesResponse.categories
                        success(categoriesResponse.categories)
                    }
                })

        }
    }
}