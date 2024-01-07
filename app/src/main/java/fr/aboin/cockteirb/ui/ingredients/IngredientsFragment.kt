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

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class IngredientsFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null
    private lateinit var ingredientsAdapter: IngredientsAdapter

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
        val view = inflater.inflate(R.layout.fragment_ingredients, container, false)

        val recycler_view_ingredients = view.findViewById<RecyclerView>(R.id.recycler_view_ingredients)
        recycler_view_ingredients.layoutManager = LinearLayoutManager(context)

        ingredientsAdapter = IngredientsAdapter()
        recycler_view_ingredients.adapter = ingredientsAdapter

        fetchData()

        return view
    }

    private fun fetchData() {
        val apiWrapper = ApiWrapper.instance

        apiWrapper.fetchIngredients(
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
        fun newInstance(param1: String, param2: String) =
            IngredientsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
