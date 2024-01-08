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

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class CategoriesFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    /*
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recycler_view_categories = view.findViewById<RecyclerView>(R.id.recycler_view_categories)
        recycler_view_categories.layoutManager = LinearLayoutManager(context)

        val adapter = CategoriesAdapter(categories)
        recycler_view_categories.adapter = adapter
    }
 */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_categories, container, false)

        // Add a new view type for category buttons
        val categoryButtonsHolder = CategoryButtonsViewHolder(view)
        categoryButtonsHolder.categoryButtonsLayout.removeAllViews()

        val apiWrapper = ApiWrapper.instance

        apiWrapper.fetchCategories(
            success = { categories ->
                // Run the UI-related code on the UI thread
                activity?.runOnUiThread {
                    // Dynamically add buttons to the layout
                    for (category in categories) {
                        val button = Button(requireContext())
                        button.text = category.name
                        button.setOnClickListener {
                            val intent = Intent(requireContext(), CocktailsActivity::class.java)
                            intent.putExtra(CocktailsActivity.CATEGORY_NAME_EXTRA, category.name)
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
        fun newInstance(param1: String, param2: String) =
            CategoriesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
