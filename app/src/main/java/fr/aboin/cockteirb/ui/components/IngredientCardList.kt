package fr.aboin.cockteirb.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter

@Composable
fun IngredientCard(
    ingredient: String,
    onClickIngredient: (ingredient: String) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(1f) // Expand card width to fill remaining space
            .clickable { onClickIngredient(ingredient) }
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .align(alignment = Alignment.Start),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberAsyncImagePainter("https://www.thecocktaildb.com/images/ingredients/${ingredient}-Small.png"),
                contentDescription = ingredient,
                modifier = Modifier
                    .size(40.dp)
            )

            Text(
                text = ingredient,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .padding(start = 16.dp)
            )
        }
    }
}

@Composable
fun IngredientCardList(
    title: String,
    ingredients: List<String>,
    onClickIngredient: (ingredient: String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        IngredientCardListHeader(title)
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 80.dp)
        ) {
            items(ingredients.size) { index ->
                IngredientCard(ingredients[index], onClickIngredient)
            }
        }
    }
}

@Composable
fun IngredientCardListHeader(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleLarge,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(1f), // Expand text width to fill remaining space
        textAlign = androidx.compose.ui.text.style.TextAlign.Center
    )
}
