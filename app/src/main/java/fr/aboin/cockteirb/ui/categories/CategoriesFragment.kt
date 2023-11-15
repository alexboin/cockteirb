package fr.aboin.cockteirb.ui.categories

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import fr.aboin.cockteirb.R
import fr.aboin.cockteirb.core.model.Category
import fr.aboin.cockteirb.core.ui.CategoriesAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CategoriesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CategoriesFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    //TODO: to connect to the API
    private val categories = listOf(
        Category("Ordinary Drink"),
        Category("Cocktail"),
        Category("Milk / Float / Shake"),
        Category("Other/Unknown"),
        Category("Cocoa"),
        Category("Shot"),
        Category("Coffee / Tea"),
        Category("Homemade Liqueur"),
        Category("Punch / Party Drink"),
        Category("Beer"),
        Category("Soft Drink / Soda")
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recycler_view_categories = view.findViewById<RecyclerView>(R.id.recycler_view_categories)
        recycler_view_categories.layoutManager = LinearLayoutManager(context)

        val adapter = CategoriesAdapter(categories)
        recycler_view_categories.adapter = adapter

    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CategoriesFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CategoriesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}