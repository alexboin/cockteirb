package fr.aboin.cockteirb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.material.tabs.TabLayout
import fr.aboin.cockteirb.ui.categories.CategoriesFragment
import fr.aboin.cockteirb.ui.ingredients.IngredientsFragment
import fr.aboin.cockteirb.ui.search.SearchFragment

class MainActivity : AppCompatActivity(), TabLayout.OnTabSelectedListener {

    private lateinit var tabLayout: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tabLayout = findViewById(R.id.tab_layout)
        tabLayout.addOnTabSelectedListener(this)

        displaySearchFragment()
    }

    private fun displaySearchFragment() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container_view, SearchFragment.newInstance())
            .commit()
    }

    private fun displayCategoriesFragment() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container_view, CategoriesFragment.newInstance())
            .commit()
    }

    private fun displayIngredientsFragment() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container_view, IngredientsFragment.newInstance())
            .commit()
    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        Log.i("MainActivity", "onTabReselected")
        tab?.let {
            when (it.position) {
                0 -> displaySearchFragment()
                1 -> displayCategoriesFragment()
                2 -> displayIngredientsFragment()
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