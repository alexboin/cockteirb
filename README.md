# Application de cocktails - Cockt'eirb
## Projet de développment mobile

👥 Contributeurs
----------------
- Alexandre Boin ([alexboin](https://github.com/alexboin/))
- Mahmound Ben Saad ([developerMAH](https://github.com/developerMAH/))

✨ Fonctionnalités développées
------------------------------
- Barre de navigation inférieure avec 4 onglets :
    - Onglet "Explore" : permet de rechercher des cocktails par nom, les résultats sont affichés dans une grille avec le nom et l'image du cocktail. Lorsqu'on clique sur un cocktail, on est redirigé vers la page de détails de sa recette. La recherche est débouncée pour éviter de faire trop de requêtes à l'API.
    - Onglet "Categories" : permet d'afficher la liste des catégories de cocktails sous forme de boutons. Lorsqu'on clique sur un bouton, on est redirigé vers la liste des cocktails de cette catégorie, avec le même format d'affichage que l'onglet "Explore".
    - Onglet "Ingredients" : permet d'afficher la liste des ingrédients de cocktails sous forme liste avec le nom et l'image de l'ingrédient. Lorsqu'on clique sur un ingrédient, on est redirigé vers la liste des cocktails contenant cet ingrédient, avec le même format d'affichage que l'onglet "Explore".
    - (Faux) Onglet "Random" : permet d'ouvrir la recette d'un cocktail aléatoire.
- Page de détails d'un cocktail : affiche le nom, l'image, la catégorie, les instructions et la liste des ingrédients nécessaires à la préparation du cocktail. Il est possible d'ajouter le cocktail à une liste de favoris.

> Nous avons fait le choix d'alterner l'utilisation des Activity, Fragment et composants Jetpack Compose pour nous familiariser avec les différentes technologies.

⚠️ Difficultés rencontrées :
----------------------------
- Beaucoup de code s'est retrouvé dupliqué pour la gestion des appels à l'API, un gros travail de factorisation a permis de réduire le nombre de classes et de lignes de code dans le wrapper servant à faire les appels à l'API TheCocktailDB.
- Nous n'avons pas trouvé comment utiliser SearchBar de Material Design 3, nous avons donc opté pour une SearchView plus basique.
- Bien que nous ayons pu développer une fonctionnalité de favoris fonctionnelle, du fait que l'API TheCocktailDB ne permet pas de récupérer les cocktails via une liste d'ID, nous n'avons finalement pas réalisé d'onglet "Favoris" car cela aurait nécessité de faire une requête à l'API pour chaque cocktail favori, ce qui aurait été peu pratique et peu performant. Mais en consultant la page de détails d'un cocktail, on peut voir si celui-ci est dans la liste des favoris ou non.