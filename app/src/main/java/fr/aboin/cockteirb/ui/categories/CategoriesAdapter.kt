package fr.aboin.cockteirb.core.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import fr.aboin.cockteirb.R
import fr.aboin.cockteirb.core.model.Category

class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var nameTextView: TextView = itemView.findViewById(R.id.fragment_container_view)
    var buttonCategory: TextView = itemView.findViewById(R.id.button_category)
}

class CategoriesAdapter(val categories: List<Category>) : RecyclerView.Adapter<CategoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.fragment_categories, parent, false)
        return CategoryViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categories[position]

        holder.nameTextView.text = category.name

        // Set a click listener for the button
        holder.buttonCategory.setOnClickListener {
            // Handle button click event here, e.g., show a toast
            val categoryName = categories[position].name
            Toast.makeText(it.context, "Button clicked for category: $categoryName", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return categories.size
    }
}

