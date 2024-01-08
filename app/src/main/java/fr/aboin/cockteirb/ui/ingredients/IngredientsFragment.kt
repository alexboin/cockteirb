package fr.aboin.cockteirb.ui.ingredients

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import fr.aboin.cockteirb.core.service.ApiWrapper
import fr.aboin.cockteirb.ui.cocktail.CocktailListActivity
import fr.aboin.cockteirb.ui.cocktail.CocktailSearchType
import fr.aboin.cockteirb.ui.components.IngredientCardList
import fr.aboin.cockteirb.ui.components.LoadingScreen

class IngredientsFragment : Fragment() {
    private lateinit var composeView: ComposeView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        composeView = ComposeView(requireContext())
        composeView.setContent {
            LoadingScreen()
        }

        fetchData()

        return composeView
    }

    private fun fetchData() {
        ApiWrapper.instance.fetchIngredients(
            success = { ingredients ->
                val ingredientsNames = ingredients.map { i -> i.name ?: "" }

                composeView.setContent {
                    IngredientCardList(
                        title = "Ingredients list",
                        ingredients = ingredientsNames,
                        onClickIngredient = { ingredientsName ->
                            val intent = Intent(requireContext(), CocktailListActivity::class.java)
                            intent.putExtra(
                                CocktailListActivity.SEARCH_TYPE_EXTRA,
                                CocktailSearchType.ByIngredient.name
                            )
                            intent.putExtra(CocktailListActivity.SEARCH_TERM_EXTRA, ingredientsName)
                            startActivity(intent)
                        }
                    )
                }
            },
            failure = { error ->
                activity?.runOnUiThread {
                    Snackbar.make(composeView, "Error while fetching ingredients: ${error.localizedMessage}", Snackbar.LENGTH_LONG).show()
                }
            }
        )
    }

    companion object {
        @JvmStatic
        fun newInstance() = IngredientsFragment()
    }
}
