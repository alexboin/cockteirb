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

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SearchFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
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

        var apiWrapper = ApiWrapper.instance

        var categoriesButton = binding.findViewById<Button>(R.id.button1)

        categoriesButton?.setOnClickListener {
            Log.i("SearchFragment", "Categories button clicked")
            apiWrapper.fetchCategories(
                success = { categories ->
                    Log.i("SearchFragment", "Categories count : ${categories.count()}")
                    for (category in categories) {
                        Log.i("SearchFragment", "Category : ${category.name}")
                    }
                },
                failure = { error ->
                    Log.i("SearchFragment", "Error : $error")
                }
            )
        }

        var cocktailButton = binding.findViewById<Button>(R.id.button2)

        cocktailButton?.setOnClickListener {
            // Open CocktailDetailsActivity, passing the cocktail id 11007
            Log.i("SearchFragment", "Cocktail button clicked")
            val intent = Intent(activity, CocktailDetailsActivity::class.java)
            intent.putExtra("cocktail_id", 11007)
            startActivity(intent)
        }

        var nonExistantCocktailButton = binding.findViewById<Button>(R.id.button3)

        nonExistantCocktailButton?.setOnClickListener {
            Log.i("SearchFragment", "Non existant cocktail button clicked")
            val intent = Intent(activity, CocktailDetailsActivity::class.java)
            intent.putExtra("cocktail_id", 404)
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
                // Handle search query submission
                Log.i("SearchFragment", "Search query submitted: $query")
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Handle search query text change
                Log.i("SearchFragment", "Search query text changed: $newText")
                return true
            }
        })

        return binding
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SearchFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SearchFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}