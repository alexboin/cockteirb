package fr.aboin.cockteirb.ui.cocktail

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import fr.aboin.cockteirb.R
import fr.aboin.cockteirb.core.model.Cocktail

class IngredientViewHolder(itemView: View) : ViewHolder(itemView) {
    val ingredientName: TextView
    val ingredientQuantity: TextView

    init {
        ingredientName = itemView.findViewById(R.id.textView_ingredient)
        ingredientQuantity = itemView.findViewById(R.id.textView_quantity)
    }
}

class IngredientAdapter(val context: Context, val cocktail: Cocktail) :
    Adapter<IngredientViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        val itemView = LayoutInflater.from(context)
            .inflate(R.layout.item_ingredient_and_quantity, parent, false)
        return IngredientViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return cocktail.ingredients?.size ?: 0
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        holder.ingredientName.text = cocktail.ingredients?.get(position)
        val quantity = cocktail.measures?.get(position)
        if (quantity != null) holder.ingredientQuantity.text = "($quantity)"
        else holder.ingredientQuantity.text = ""
    }
}