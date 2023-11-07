package fr.aboin.cockteirb.core.service

import fr.aboin.cockteirb.core.model.Category

class CategoriesFetcher {
    // API route
    // GET v1/1/list.php?c=list
    fun fetchCategories(
        success: (List<Category>) -> Unit,
        failure: (Error) -> Unit
    ) {
        // Appel à OKHTTP
        // Si succès
        success(emptyList())
        // Si erreur
        // failure(...)
    }
}