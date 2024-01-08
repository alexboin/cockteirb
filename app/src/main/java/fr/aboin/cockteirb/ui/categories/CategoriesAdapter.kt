package fr.aboin.cockteirb.ui.categories

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import fr.aboin.cockteirb.R
import fr.aboin.cockteirb.core.model.Category

class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var nameTextView: TextView = itemView.findViewById(R.id.textview_selected_category)
}

class CategoryButtonsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var categoryButtonsLayout: LinearLayout = itemView.findViewById(R.id.category_buttons_layout)
}

class CategoriesAdapter(val categories: List<Category>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val VIEW_TYPE_CATEGORY = 1
    private val VIEW_TYPE_BUTTONS = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_CATEGORY -> {
                val itemView = LayoutInflater.from(parent.context).inflate(R.layout.fragment_categories, parent, false)
                CategoryViewHolder(itemView)
            }
            VIEW_TYPE_BUTTONS -> {
                val itemView = LayoutInflater.from(parent.context).inflate(R.layout.fragment_categories_buttons, parent, false)
                CategoryButtonsViewHolder(itemView)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CategoryViewHolder -> {
                val category = categories[position]
                holder.nameTextView.text = category.name
            }
            is CategoryButtonsViewHolder -> {
                // Dynamically add buttons to the layout here
                val categoryButtonsLayout = holder.categoryButtonsLayout
                for (category in categories) {
                    val button = Button(holder.itemView.context)
                    button.text = category.name
                    //TODO: use the api
                    button.setOnClickListener {
                        Toast.makeText(it.context, "Button clicked for category: ${category.name}", Toast.LENGTH_SHORT).show()
                    }
                    categoryButtonsLayout.addView(button)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        // Return the total number of items including the buttons
        return categories.size + 1
    }

    override fun getItemViewType(position: Int): Int {
        // Return the appropriate view type for each position
        return if (position == categories.size) {
            VIEW_TYPE_BUTTONS
        } else {
            VIEW_TYPE_CATEGORY
        }
    }
}
