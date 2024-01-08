package fr.aboin.cockteirb.ui.cocktail

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.aboin.cockteirb.R
import fr.aboin.cockteirb.core.model.CocktailSummary
import fr.aboin.cockteirb.core.service.ApiWrapper
import fr.aboin.cockteirb.ui.categories.CocktailsAdapter

enum class CocktailSearchType {
    ByName,
    ByCategory,
    ByIngredient
}

class CocktailListActivity : AppCompatActivity() {

    companion object {
        const val SEARCH_TYPE_EXTRA = "search_type"
        const val SEARCH_TERM_EXTRA = "search_term"
    }

    private lateinit var cocktailsAdapter: CocktailsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cocktail_list)

        // Retrieve the type of search to perform and the search term
        val searchType: CocktailSearchType = CocktailSearchType.valueOf(intent.getStringExtra(SEARCH_TYPE_EXTRA) ?: "")
        val searchTerm: String = intent.getStringExtra(SEARCH_TERM_EXTRA) ?: ""

        val textView: TextView = findViewById(R.id.textView)

        when (searchType) {
            CocktailSearchType.ByName -> textView.text = "Cocktails matching \"$searchTerm\""
            CocktailSearchType.ByCategory -> textView.text = "Cocktails in category \"$searchTerm\""
            CocktailSearchType.ByIngredient -> textView.text = "Cocktails containing \"$searchTerm\""
        }

        // Initialize the adapter
        cocktailsAdapter = CocktailsAdapter { cocktail ->
            // Handle the click on a cocktail
            val intent = Intent(this, CocktailDetailsActivity::class.java)
            intent.putExtra(CocktailDetailsActivity.COCKTAIL_ID_EXTRA, cocktail.id)
            startActivity(intent)
        }

        // Configure the RecyclerView
        val recyclerView: RecyclerView = findViewById(R.id.recycler_view_cocktails)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = cocktailsAdapter

        // Load the cocktails for the specified category
        loadCocktails(searchType, searchTerm)
    }

    private fun loadCocktails(searchType: CocktailSearchType, searchTerm: String) {
        val successCallback = { cocktails: List<CocktailSummary> ->
            runOnUiThread {
                cocktailsAdapter.setCocktails(cocktails)
            }
        }

        val errorCallback = { error: Error ->
            runOnUiThread {
                // Handle the error TODO
            }
        }

        when (searchType) {
            CocktailSearchType.ByName -> ApiWrapper.instance.fetchCocktailsByName(searchTerm, successCallback, errorCallback)
            CocktailSearchType.ByCategory -> ApiWrapper.instance.fetchCocktailsByCategory(searchTerm, successCallback, errorCallback)
            CocktailSearchType.ByIngredient -> ApiWrapper.instance.fetchCocktailsByIngredient(searchTerm, successCallback, errorCallback)
        }
    }
}
