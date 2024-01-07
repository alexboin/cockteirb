package fr.aboin.cockteirb.ui.categories

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.aboin.cockteirb.R
import fr.aboin.cockteirb.core.model.CocktailSummary

class CocktailsAdapter : RecyclerView.Adapter<CocktailsAdapter.CocktailViewHolder>() {

    private var cocktails: List<CocktailSummary> = emptyList()

    fun setCocktails(cocktails: List<CocktailSummary>) {
        this.cocktails = cocktails
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CocktailViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_cocktail, parent, false)
        return CocktailViewHolder(view)
    }

    override fun onBindViewHolder(holder: CocktailViewHolder, position: Int) {
        holder.bind(cocktails[position])
    }

    override fun getItemCount(): Int {
        return cocktails.size
    }

    class CocktailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val cocktailImageView: ImageView = itemView.findViewById(R.id.cocktailImageView)
        private val cocktailNameTextView: TextView = itemView.findViewById(R.id.cocktailNameTextView)

        fun bind(cocktail: CocktailSummary) {
            Picasso.get().load(cocktail.imageURL).into(cocktailImageView)
            cocktailNameTextView.text = cocktail.title
        }
    }
}
