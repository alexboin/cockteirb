package fr.aboin.cockteirb.core.service

import android.content.Context
import android.content.SharedPreferences

/**
 * A singleton class to manage favorite cocktails
 */
class FavoriteManager private constructor(private val context: Context) {
    companion object {
        private const val PREFERENCES_NAME = "FavoriteCocktails"
        private const val KEY_FAVORITES = "favoriteIds"

        private var instance: FavoriteManager? = null

        fun getInstance(context: Context): FavoriteManager {
            if (instance == null) {
                instance = FavoriteManager(context)
            }
            return instance!!
        }
    }

    private val preferences: SharedPreferences =
        context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

    /**
     * Get the list of favorite cocktails
     * @return The list of favorite cocktails
     */
    fun getFavoriteCocktailIds(): Set<String> {
        return preferences.getStringSet(KEY_FAVORITES, emptySet()) ?: emptySet()
    }

    /**
     * Add a cocktail to the list of favorite cocktails
     * @param cocktailId The ID of the cocktail to add
     */
    fun addFavoriteCocktail(cocktailId: String) {
        val favoriteIds = getFavoriteCocktailIds().toMutableSet()
        favoriteIds.add(cocktailId)
        saveFavoriteCocktailIds(favoriteIds)
    }

    /**
     * Remove a cocktail from the list of favorite cocktails
     * @param cocktailId The ID of the cocktail to remove
     */
    fun removeFavoriteCocktail(cocktailId: String) {
        val favoriteIds = getFavoriteCocktailIds().toMutableSet()
        favoriteIds.remove(cocktailId)
        saveFavoriteCocktailIds(favoriteIds)
    }

    /**
     * Check if a cocktail is in the list of favorite cocktails
     * @param cocktailId The ID of the cocktail to check
     * @return true if the cocktail is in the list of favorite cocktails, false otherwise
     */
    fun isFavorite(cocktailId: String): Boolean {
        return getFavoriteCocktailIds().contains(cocktailId)
    }

    private fun saveFavoriteCocktailIds(ids: Set<String>) {
        preferences.edit().putStringSet(KEY_FAVORITES, ids).apply()
    }
}
