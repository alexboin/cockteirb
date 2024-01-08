package fr.aboin.cockteirb.ui.search

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar

import fr.aboin.cockteirb.R
import fr.aboin.cockteirb.core.service.ApiWrapper
import fr.aboin.cockteirb.ui.cocktail.CocktailDetailsActivity
import fr.aboin.cockteirb.ui.cocktail.CocktailListActivity
import fr.aboin.cockteirb.ui.cocktail.CocktailSearchType

class SearchFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        // Bind button to click listener
        /*var categoriesFetcher = CategoriesFetcher.getInstance()
        var button = view?.findViewById<Button>(R.id.fetch_categories_button)
        Log.i("MainActivity", "Button : $button")
        button?.setOnClickListener {
            Log.i("MainActivity", "Button clicked")
            categoriesFetcher.fetchCategories(
                success = { categories ->
                    Log.i("MainActivity", "Categories count : ${categories.count()}")
                    for (category in categories) {
                        Log.i("MainActivity", "Category : ${category.name}")
                    }
                },
                failure = { error ->
                    Log.i("MainActivity", "Error : $error")
                }
            )
        }*/

        var binding = inflater.inflate(R.layout.fragment_search, container, false)

        var cocktailButton = binding.findViewById<Button>(R.id.button1)

        cocktailButton?.setOnClickListener {
            // Open CocktailDetailsActivity, passing the cocktail id 11007
            Log.i("SearchFragment", "Cocktail button clicked")
            val intent = Intent(activity, CocktailDetailsActivity::class.java)
            intent.putExtra(CocktailDetailsActivity.COCKTAIL_ID_EXTRA, "11007")
            startActivity(intent)
        }

        var nonExistantCocktailButton = binding.findViewById<Button>(R.id.button2)

        nonExistantCocktailButton?.setOnClickListener {
            Log.i("SearchFragment", "Non existant cocktail button clicked")
            val intent = Intent(activity, CocktailDetailsActivity::class.java)
            intent.putExtra(CocktailDetailsActivity.COCKTAIL_ID_EXTRA, "404")
            startActivity(intent)
        }

        var randomCocktailButton = binding.findViewById<Button>(R.id.button3)

        randomCocktailButton?.setOnClickListener {
            Log.i("SearchFragment", "Random cocktail button clicked")
            val intent = Intent(activity, CocktailDetailsActivity::class.java)
            intent.putExtra(CocktailDetailsActivity.COCKTAIL_ID_EXTRA, "random")
            startActivity(intent)
        }

        val toolbar: MaterialToolbar = binding.findViewById(R.id.toolbar)
        if (activity is AppCompatActivity) {
            (activity as AppCompatActivity).setSupportActionBar(toolbar)
        }

        val searchView: SearchView = binding.findViewById(R.id.searchView)

        // Set up SearchView listener or perform actions as needed
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                val intent = Intent(activity, CocktailListActivity::class.java)
                intent.putExtra(CocktailListActivity.SEARCH_TYPE_EXTRA, CocktailSearchType.ByName.name)
                intent.putExtra(CocktailListActivity.SEARCH_TERM_EXTRA, query)
                startActivity(intent)
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                // Do nothing
                return true
            }
        })

        return binding
    }

    companion object {
        @JvmStatic
        fun newInstance() = SearchFragment()
    }
}