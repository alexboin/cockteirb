package fr.aboin.cockteirb.ui.cocktail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import fr.aboin.cockteirb.R
import fr.aboin.cockteirb.core.model.Cocktail
import fr.aboin.cockteirb.core.service.ApiWrapper
import fr.aboin.cockteirb.core.service.FavoriteManager
import fr.aboin.cockteirb.databinding.ActivityCocktailDetailsBinding

class CocktailDetailsActivity : AppCompatActivity() {

    companion object {
        const val COCKTAIL_ID_EXTRA = "cocktail_id"
    }

    private lateinit var binding: ActivityCocktailDetailsBinding

    private var cocktail: Cocktail? = null
    private var isFavorite: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCocktailDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ctrlMainLayout.visibility = View.GONE
        binding.ctrlActivityIndicator.visibility = View.VISIBLE

        // Get extras from the intent
        val cocktailId = intent.getStringExtra(COCKTAIL_ID_EXTRA)

        // If the cocktailId is -1, we don't have a cocktailId, so we can't display the details
        // Show an error message in a dialog
        if (cocktailId == null) {
            val alert = AlertDialog.Builder(this)
            alert.setTitle("Error")
            alert.setCancelable(false)
            alert.setMessage("No cocktail id provided, click OK to close the activity")
            alert.setPositiveButton("OK") { dialog, which ->
                finish()
            }
            runOnUiThread {
                alert.show()
            }
        } else {
            ApiWrapper.instance.fetchCocktailDetails(
                cocktailId,
                success = { cocktail ->
                    this.cocktail = cocktail

                    this.isFavorite = FavoriteManager.getInstance(this).isFavorite(cocktail.id.toString())
                    runOnUiThread {
                        if (isFavorite) {
                            binding.favoriteButton.setImageResource(R.drawable.star_icon_filled)
                        } else {
                            binding.favoriteButton.setImageResource(R.drawable.star_icon)
                        }
                    }

                    binding.favoriteButton.setOnClickListener {
                        if (isFavorite) {
                            FavoriteManager.getInstance(this).removeFavoriteCocktail(cocktail.id.toString())
                            runOnUiThread {
                                binding.favoriteButton.setImageResource(R.drawable.star_icon)
                                Snackbar.make(binding.root, "Removed from favorites", Snackbar.LENGTH_SHORT).show()
                            }
                        } else {
                            FavoriteManager.getInstance(this).addFavoriteCocktail(cocktail.id.toString())
                            runOnUiThread {
                                binding.favoriteButton.setImageResource(R.drawable.star_icon_filled)
                                Snackbar.make(binding.root, "Added to favorites", Snackbar.LENGTH_SHORT).show()
                            }
                        }
                        isFavorite = !isFavorite
                    }

                    runOnUiThread {
                        Picasso.get().load(cocktail.imageURL).into(binding.imageViewCocktail)
                        binding.titleTextView.text = cocktail.title
                        binding.categoryValueTextView.text = cocktail.category
                        binding.servedInValueTextView.text = cocktail.glass
                        binding.instructionsValueTextView.text = cocktail.instructions?.get("EN")

                        val ingredientsText = cocktail.ingredients?.mapIndexed { index, ingredient ->
                            val measure = cocktail.measures?.count().let { measuresCount ->
                                if (index < measuresCount!!) {
                                    cocktail.measures?.get(index)
                                } else {
                                    null
                                }
                            }
                            if (measure != null) {
                                "- $ingredient ($measure)\n"
                            } else {
                                "- $ingredient\n"
                            }
                        }?.joinToString(separator = "")

                        binding.ingredientsValueTextView.text = ingredientsText

                        binding.ctrlMainLayout.visibility = View.VISIBLE
                        binding.ctrlActivityIndicator.visibility = View.GONE
                    }
                },
                failure = { error ->
                    val alert = AlertDialog.Builder(this)
                    alert.setTitle("Error")
                    alert.setMessage(error.message)
                    alert.setCancelable(false)
                    alert.setPositiveButton("OK") { dialog, which ->
                        finish()
                    }
                    runOnUiThread {
                        alert.show()
                    }
                }
            )
        }
    }
}