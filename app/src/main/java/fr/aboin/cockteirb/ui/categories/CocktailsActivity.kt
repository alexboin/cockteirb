package fr.aboin.cockteirb.ui.categories

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.aboin.cockteirb.R
import fr.aboin.cockteirb.core.model.CocktailSummary
import fr.aboin.cockteirb.core.service.ApiWrapper


class CocktailsActivity : AppCompatActivity() {

    companion object {
        const val CATEGORY_NAME_EXTRA = "category_name"
    }

    private lateinit var category: String
    private lateinit var apiWrapper: ApiWrapper
    private lateinit var cocktailsAdapter: CocktailsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cocktails)

        // Retrieve the category from the intent
        category = intent.getStringExtra(CATEGORY_NAME_EXTRA) ?: ""

        // Initialize the DataFetcher
        apiWrapper = ApiWrapper.instance

        // Initialize the adapter
        cocktailsAdapter = CocktailsAdapter()

        // Configure the RecyclerView
        val recyclerView: RecyclerView = findViewById(R.id.recycler_view_cocktails)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = cocktailsAdapter

        // Load the cocktails for the specified category
        loadCocktails()
    }

    private fun loadCocktails() {
        apiWrapper.fetchCocktailsByCategory(
            category = category,
            success = { cocktails: List<CocktailSummary> ->
                runOnUiThread {
                    cocktailsAdapter.setCocktails(cocktails)
                }
            },
            failure = { error ->
                runOnUiThread {
                    // Handle the error TODO
                }
            }
        )
    }

}
