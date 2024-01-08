package fr.aboin.cockteirb.ui.explore

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.appbar.MaterialToolbar

import fr.aboin.cockteirb.R
import fr.aboin.cockteirb.ui.cocktail.CocktailListActivity
import fr.aboin.cockteirb.ui.cocktail.CocktailListFragment
import fr.aboin.cockteirb.ui.cocktail.CocktailSearchType
import fr.aboin.cockteirb.ui.ingredients.IngredientsFragment
import kotlinx.coroutines.delay

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

            var queryTextChangedJob: kotlinx.coroutines.Job? = null

            override fun onQueryTextChange(p0: String?): Boolean {
                if (queryTextChangedJob?.isActive == true) {
                    queryTextChangedJob?.cancel()
                }
                queryTextChangedJob = lifecycleScope.launchWhenStarted {
                    delay(300L) // Debounce query for 300ms
                    childFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragment_container_view, CocktailListFragment.newInstance(p0 ?: ""))
                        .commit()
                }
                return true
            }
        })

        childFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container_view, CocktailListFragment.newInstance(""))
            .commit()

        return binding
    }

    companion object {
        @JvmStatic
        fun newInstance() = ExploreFragment()
    }
}