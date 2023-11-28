package fr.aboin.cockteirb.ui.cocktailDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import fr.aboin.cockteirb.R

class CocktailDetailsFragment : DialogFragment() {

    companion object {
        const val COCKTAIL_NAME = "cocktail_name"
        const val COCKTAIL_CATEGORY = "cocktail_category"
        const val COCKTAIL_SERVED_IN = "cocktail_served_in"
        const val COCKTAIL_INSTRUCTIONS = "cocktail_instructions"
        const val COCKTAIL_INGREDIENTS = "cocktail_ingredients"

        fun newInstance(): CocktailDetailsFragment {
            return newInstance(
                "Margarita",
                "Cocktail",
                "Cocktail Glass",
                "Mix ingredients, shake well, and strain into a chilled glass.",
                "2 oz Tequila\n1 oz Lime Juice\n1 oz Triple Sec"
            )
        }

        fun newInstance(
            name: String,
            category: String,
            servedIn: String,
            instructions: String,
            ingredients: String
        ): CocktailDetailsFragment {
            val fragment = CocktailDetailsFragment()
            val args = Bundle().apply {
                putString(COCKTAIL_NAME, name)
                putString(COCKTAIL_CATEGORY, category)
                putString(COCKTAIL_SERVED_IN, servedIn)
                putString(COCKTAIL_INSTRUCTIONS, instructions)
                putString(COCKTAIL_INGREDIENTS, ingredients)
            }
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cocktail_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cocktailName = arguments?.getString(COCKTAIL_NAME) ?: ""
        val cocktailCategory = arguments?.getString(COCKTAIL_CATEGORY) ?: ""
        val cocktailServedIn = arguments?.getString(COCKTAIL_SERVED_IN) ?: ""
        val cocktailInstructions = arguments?.getString(COCKTAIL_INSTRUCTIONS) ?: ""
        val cocktailIngredients = arguments?.getString(COCKTAIL_INGREDIENTS) ?: ""

        view.findViewById<TextView>(R.id.pop_up_cocktail_name)?.text = cocktailName
        view.findViewById<TextView>(R.id.pop_up_cocktail_category)?.text = cocktailCategory
        view.findViewById<TextView>(R.id.pop_up_cocktail_served_in)?.text = cocktailServedIn
        view.findViewById<TextView>(R.id.pop_up_cocktail_instructions)?.text = cocktailInstructions
        view.findViewById<TextView>(R.id.pop_up_cocktail_ingredients)?.text = cocktailIngredients
    }
}
