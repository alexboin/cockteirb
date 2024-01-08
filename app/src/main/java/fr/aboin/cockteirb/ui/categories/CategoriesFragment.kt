package fr.aboin.cockteirb.ui.categories

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import fr.aboin.cockteirb.R
import fr.aboin.cockteirb.core.service.ApiWrapper
import fr.aboin.cockteirb.ui.cocktail.CocktailListActivity
import fr.aboin.cockteirb.ui.cocktail.CocktailSearchType

class CategoriesFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_categories, container, false)

        // Add a new view type for category buttons
        val categoryButtonsHolder = CategoryButtonsViewHolder(view)
        categoryButtonsHolder.categoryButtonsLayout.removeAllViews()

        ApiWrapper.instance.fetchCategories(
            success = { categories ->
                // Run the UI-related code on the UI thread
                activity?.runOnUiThread {
                    // Dynamically add buttons to the layout
                    for (category in categories) {
                        val button = Button(requireContext())
                        button.text = category.name
                        button.setOnClickListener {
                            val intent = Intent(requireContext(), CocktailListActivity::class.java)
                            intent.putExtra(CocktailListActivity.SEARCH_TYPE_EXTRA, CocktailSearchType.ByCategory.name)
                            intent.putExtra(CocktailListActivity.SEARCH_TERM_EXTRA, category.name)
                            startActivity(intent)
                        }
                        categoryButtonsHolder.categoryButtonsLayout.addView(button)
                    }
                }
            },
            failure = { error ->
                // Run the UI-related code on the UI thread
                activity?.runOnUiThread {
                    // Handle the error
                    Toast.makeText(requireContext(), "Error fetching categories: ${error.localizedMessage}", Toast.LENGTH_SHORT).show()
                }
            }
        )

        return view
    }


    companion object {
        @JvmStatic
        fun newInstance() = CategoriesFragment()
    }
}
