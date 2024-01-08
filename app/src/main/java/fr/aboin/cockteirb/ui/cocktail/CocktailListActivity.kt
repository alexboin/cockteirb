package fr.aboin.cockteirb.ui.cocktail

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.platform.ComposeView
import fr.aboin.cockteirb.core.model.CocktailSummary
import fr.aboin.cockteirb.core.service.ApiWrapper
import fr.aboin.cockteirb.ui.categories.CocktailsAdapter
import fr.aboin.cockteirb.ui.components.CocktailCardList
import fr.aboin.cockteirb.ui.components.ErrorScreen
import fr.aboin.cockteirb.ui.components.LoadingScreen
import fr.aboin.cockteirb.ui.components.NoContentScreen

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
    private lateinit var composeView: ComposeView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        composeView = ComposeView(this)
        setContentView(composeView)

        // Display the loading screen
        runOnUiThread { setContent { LoadingScreen() } }

        // Retrieve the type of search to perform and the search term
        val searchType: CocktailSearchType =
            CocktailSearchType.valueOf(intent.getStringExtra(SEARCH_TYPE_EXTRA) ?: "")
        val searchTerm: String = (intent.getStringExtra(SEARCH_TERM_EXTRA) ?: "").trim()

        val title = when (searchType) {
            CocktailSearchType.ByName -> "Cocktails matching \"$searchTerm\" :"
            CocktailSearchType.ByCategory -> "Cocktails in category \"$searchTerm\" :"
            CocktailSearchType.ByIngredient -> "Cocktails containing \"$searchTerm\" :"
        }

        // Load the cocktails
        loadCocktails(title, searchType, searchTerm)
    }

    private fun loadCocktails(title: String, searchType: CocktailSearchType, searchTerm: String) {
        val errorCallback = { error: Error ->
            runOnUiThread {
                setContent {
                    ErrorScreen(error.message ?: "Error") {
                        loadCocktails(
                            title,
                            searchType,
                            searchTerm
                        )
                    }
                }
            }
        }

        val successCallback = { cocktails: List<CocktailSummary> ->
            runOnUiThread {
                if (cocktails.isEmpty()) {
                    val message = when (searchType) {
                        CocktailSearchType.ByName -> "Try to change your keywords"
                        CocktailSearchType.ByCategory -> "This category does not contain any cocktail"
                        CocktailSearchType.ByIngredient -> "This ingredient isn't used in any cocktail"
                    }

                    setContent {
                        NoContentScreen(
                            message,
                            onGoBack ={ finish() }
                        )
                    }
                } else {
                    setContent {
                        CocktailCardList(title, cocktails) { cocktailId ->
                            val intent = Intent(this, CocktailDetailsActivity::class.java)
                            intent.putExtra(CocktailDetailsActivity.COCKTAIL_ID_EXTRA, cocktailId)
                            startActivity(intent)
                        }
                    }
                }
            }
        }

        // Display the loading screen
        runOnUiThread { setContent { LoadingScreen() } }

        // Then load the cocktails according to the search type and term
        when (searchType) {
            CocktailSearchType.ByName -> ApiWrapper.instance.fetchCocktailsByName(
                searchTerm,
                successCallback,
                errorCallback
            )

            CocktailSearchType.ByCategory -> ApiWrapper.instance.fetchCocktailsByCategory(
                searchTerm,
                successCallback,
                errorCallback
            )

            CocktailSearchType.ByIngredient -> ApiWrapper.instance.fetchCocktailsByIngredient(
                searchTerm,
                successCallback,
                errorCallback
            )
        }
    }
}
