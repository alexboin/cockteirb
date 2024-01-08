package fr.aboin.cockteirb.ui.ingredients

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.aboin.cockteirb.R
import fr.aboin.cockteirb.core.service.ApiWrapper

class IngredientsFragment : Fragment() {
    private lateinit var ingredientsAdapter: IngredientsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_ingredients, container, false)

        val recycler_view_ingredients = view.findViewById<RecyclerView>(R.id.recycler_view_ingredients)
        recycler_view_ingredients.layoutManager = LinearLayoutManager(context)

        ingredientsAdapter = IngredientsAdapter()
        recycler_view_ingredients.adapter = ingredientsAdapter

        fetchData()

        return view
    }

    private fun fetchData() {
        ApiWrapper.instance.fetchIngredients(
            success = { ingredients ->
                activity?.runOnUiThread {
                    ingredientsAdapter.setIngredients(ingredients)
                }
            },
            failure = { error ->
                activity?.runOnUiThread {
                    Toast.makeText(
                        requireContext(),
                        "Error fetching ingredients: ${error.localizedMessage}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        )
    }

    companion object {
        @JvmStatic
        fun newInstance() = IngredientsFragment()
    }
}
