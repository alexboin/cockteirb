package fr.aboin.cockteirb.ui.ingredients

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.aboin.cockteirb.R
import fr.aboin.cockteirb.core.model.Ingredient

class IngredientViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var buttonIngredient: Button = itemView.findViewById(R.id.button_ingredient)
}


class IngredientButtonsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var ingredientButtonsLayout: LinearLayout = itemView.findViewById(R.id.ingredient_buttons_layout)
}

/*  version one buttons

class IngredientsAdapter : RecyclerView.Adapter<IngredientViewHolder>() {

    private var ingredients: List<Ingredient> = emptyList()

    // Méthode pour définir les ingrédients ultérieurement
    fun setIngredients(ingredients: List<Ingredient>) {
        this.ingredients = ingredients
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_ingredient, parent, false)
        return IngredientViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        val ingredient = ingredients[position]
        holder.nameTextView.text = ingredient.name
    }

    override fun getItemCount(): Int {
        return ingredients.size
    }
}*/
class IngredientsAdapter : RecyclerView.Adapter<IngredientsAdapter.IngredientViewHolder>() {

    private var ingredients: List<Ingredient> = emptyList()

    fun setIngredients(ingredients: List<Ingredient>) {
        this.ingredients = ingredients
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        val button = Button(parent.context)
        button.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        return IngredientViewHolder(button)
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        val ingredient = ingredients[position]
        holder.button.text = ingredient.name
    }

    override fun getItemCount(): Int {
        return ingredients.size
    }

    class IngredientViewHolder(val button: Button) : RecyclerView.ViewHolder(button)
}
