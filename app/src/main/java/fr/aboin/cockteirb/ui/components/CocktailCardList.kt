package fr.aboin.cockteirb.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import fr.aboin.cockteirb.core.model.CocktailSummary

@Composable
fun CocktailCardList(title: String?, cocktails: List<CocktailSummary>, onClickCocktail: (id: String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        if (title != null) CocktailCardListHeader(title)
        LazyVerticalGrid(
            columns = GridCells.Adaptive(160.dp),
            modifier = Modifier
                .padding(bottom = if (title != null) 0.dp else 80.dp)
        ) {
            items(cocktails.size) { index ->
                CocktailCard(cocktails[index], onClickCocktail)
            }
        }
    }
}

@Composable
fun CocktailCardListHeader(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleLarge,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(1f), // Expand text width to fill remaining space
        textAlign = androidx.compose.ui.text.style.TextAlign.Center
    )
}

@Composable
fun CocktailCard(cocktail: CocktailSummary, onClickCocktail: (id: String) -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(1f) // Expand card width to fill remaining space
            .clickable { onClickCocktail(cocktail.id ?: "") }
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .align(alignment = Alignment.CenterHorizontally)
        ) {
            Image(
                painter = rememberAsyncImagePainter(cocktail.imageURL),
                contentDescription = cocktail.title,
                modifier = Modifier
                    .size(120.dp)
                    .align(alignment = Alignment.CenterHorizontally)
            )

            Text(
                text = cocktail.title ?: "",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .align(alignment = Alignment.CenterHorizontally)
            )
        }
    }
}

