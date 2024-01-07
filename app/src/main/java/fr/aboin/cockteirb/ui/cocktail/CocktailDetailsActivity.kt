package fr.aboin.cockteirb.ui.cocktail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.squareup.picasso.Picasso
import fr.aboin.cockteirb.core.model.Cocktail
import fr.aboin.cockteirb.core.service.ApiWrapper
import fr.aboin.cockteirb.databinding.ActivityCocktailDetailsBinding

class CocktailDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCocktailDetailsBinding

    private var cocktail: Cocktail? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCocktailDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ctrlMainLayout.visibility = View.GONE
        binding.ctrlActivityIndicator.visibility = View.VISIBLE

        // Get extras from the intent
        val cocktailId = intent.getIntExtra("cocktail_id", -1)

        // If the cocktailId is -1, we don't have a cocktailId, so we can't display the details
        // Show an error message in a dialog
        if (cocktailId == -1) {
            val alert = AlertDialog.Builder(this)
            alert.setTitle("Error")
            alert.setMessage("No cocktail id provided, click OK to close the activity")
            alert.setPositiveButton("OK") { dialog, which ->
                finish()
            }
            runOnUiThread {
                alert.show()
            }
        } else {
            ApiWrapper.getInstance().fetchCocktailDetails(
                cocktailId,
                success = { cocktail ->
                    this.cocktail = cocktail
                    Log.i("CocktailDetailsActivity", "Cocktail: $cocktail")
                    runOnUiThread {
                        Picasso.get().load(cocktail.imageURL).into(binding.imageViewCocktail)
                        binding.popUpCocktailName.text = cocktail.title
                        binding.popUpCocktailCategory.text = cocktail.category
                        binding.popUpCocktailInstructions.text = cocktail.instructions?.get("EN")

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

                        binding.popUpCocktailIngredients.text = ingredientsText

                        binding.ctrlMainLayout.visibility = View.VISIBLE
                        binding.ctrlActivityIndicator.visibility = View.GONE
                    }
                },
                failure = { error ->
                    val alert = AlertDialog.Builder(this)
                    alert.setTitle("Error")
                    alert.setMessage(error.message)
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