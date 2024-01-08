package fr.aboin.cockteirb.ui.cocktail

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import fr.aboin.cockteirb.core.model.CocktailSummary
import fr.aboin.cockteirb.core.service.ApiWrapper
import fr.aboin.cockteirb.ui.components.CocktailCardList
import fr.aboin.cockteirb.ui.components.ErrorScreen
import fr.aboin.cockteirb.ui.components.LoadingScreen
import fr.aboin.cockteirb.ui.components.NoContentScreen

private const val ARG_PARAM1 = ""

class CocktailListFragment : Fragment() {
    private lateinit var composeView: ComposeView

    private var param1: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        composeView = ComposeView(requireContext())
        composeView.setContent {
            LoadingScreen()
        }

        loadCocktails(param1 ?: "")

        return composeView
    }

    private fun loadCocktails(searchTerm: String) {
        val errorCallback = { error: Error ->
            composeView.setContent {
                ErrorScreen(error.message ?: "Error") {
                    loadCocktails(
                        searchTerm
                    )
                }
            }
        }

        val successCallback = { cocktails: List<CocktailSummary> ->
            if (cocktails.isEmpty()) {
                val message = "Try to change your keywords"

                composeView.setContent {
                    NoContentScreen(
                        message,
                        onGoBack = { },
                        showGoBackButton = false
                    )
                }
            } else {
                composeView.setContent {
                    CocktailCardList(null, cocktails) { cocktailId ->
                        val intent = Intent(requireContext(), CocktailDetailsActivity::class.java)
                        intent.putExtra(CocktailDetailsActivity.COCKTAIL_ID_EXTRA, cocktailId)
                        startActivity(intent)
                    }
                }
            }
        }

        // Display the loading screen
        composeView.setContent { LoadingScreen() }

        // Then load the cocktails according to the search term
        ApiWrapper.instance.fetchCocktailsByName(
            searchTerm,
            successCallback,
            errorCallback
        )
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            CocktailListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }
}