package fr.aboin.cockteirb

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.material.tabs.TabLayout
import fr.aboin.cockteirb.ui.categories.CategoriesFragment
import fr.aboin.cockteirb.ui.cocktail.CocktailDetailsActivity
import fr.aboin.cockteirb.ui.ingredients.IngredientsFragment
import fr.aboin.cockteirb.ui.explore.ExploreFragment

class MainActivity : AppCompatActivity(), TabLayout.OnTabSelectedListener {

    private lateinit var tabLayout: TabLayout
    private var selectedTabPosition: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tabLayout = findViewById(R.id.tab_layout)
        tabLayout.addOnTabSelectedListener(this)

        displaySearchFragment()
    }

    private fun displaySearchFragment() {
        selectedTabPosition = 0
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container_view, ExploreFragment.newInstance())
            .commit()
    }

    private fun displayCategoriesFragment() {
        selectedTabPosition = 1
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container_view, CategoriesFragment.newInstance())
            .commit()
    }

    private fun displayIngredientsFragment() {
        selectedTabPosition = 2
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container_view, IngredientsFragment.newInstance())
            .commit()
    }

    private fun displayRandomCocktail() {
        val intent = Intent(this, CocktailDetailsActivity::class.java)
        intent.putExtra(CocktailDetailsActivity.COCKTAIL_ID_EXTRA, "random")
        startActivity(intent)

        // Reselect the previous tab
        tabLayout.getTabAt(selectedTabPosition)?.select()
    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        Log.i("MainActivity", "onTabReselected")
        tab?.let {
            when (it.position) {
                0 -> displaySearchFragment()
                1 -> displayCategoriesFragment()
                2 -> displayIngredientsFragment()
                3 -> displayRandomCocktail()
            }
        }
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {
        Log.i("MainActivity", "onTabUnselected")
    }

    override fun onTabReselected(tab: TabLayout.Tab?) {
        Log.i("MainActivity", "onTabReselected")
    }
}