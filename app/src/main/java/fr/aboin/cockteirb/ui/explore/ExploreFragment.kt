package fr.aboin.cockteirb.ui.explore

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar

import fr.aboin.cockteirb.R
import fr.aboin.cockteirb.ui.cocktail.CocktailListActivity
import fr.aboin.cockteirb.ui.cocktail.CocktailSearchType

class ExploreFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var binding = inflater.inflate(R.layout.fragment_explore, container, false)

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
        fun newInstance() = ExploreFragment()
    }
}