package com.example.amphibians.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.example.amphibians.models.Amphibian
import com.example.amphibians.ui.theme.AmphibiansTheme


@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = viewModel(factory = MainViewModel.Factory)
) {
    when (viewModel.uiState) {
        is UiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is UiState.Success -> AmphibianList(
            amphibians = (viewModel.uiState as UiState.Success).amphibians,
            modifier = modifier
        )

        is UiState.Error -> ErrorScreen(
            message = (viewModel.uiState as UiState.Error).message,
            onRetry = viewModel::getAmphibians,
            modifier = modifier.fillMaxSize()
        )
    }
}

@Composable
fun LoadingScreen(
    modifier: Modifier = Modifier
) {
    Text(
        "Loading...",
        modifier = modifier
            .wrapContentSize()
    )
}

@Composable
fun ErrorScreen(
    message: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        Text(
            message,
            modifier = Modifier
        )
        Spacer(
            modifier = Modifier.height(16.dp)
        )
        TextButton(onClick = onRetry) {
            Text("Retry")
        }
    }
}

@Composable
fun AmphibianList(
    amphibians: List<Amphibian>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(amphibians, key = { amphibian -> amphibian.name }) { amphibian ->
            AmphibianCard(amphibian)
        }
    }
}

@Composable
fun AmphibianCard(
    amphibian: Amphibian,
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = modifier
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
        ) {
            Text(
                "${amphibian.name} (${amphibian.type})",
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .wrapContentWidth()
            )
            AsyncImage(
                model = amphibian.imgSrc,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
            )

            Text(
                amphibian.description,
                textAlign = TextAlign.Justify,
                modifier = Modifier
                    .padding(8.dp)
            )
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    AmphibiansTheme {
        HomeScreen()
    }
}

@Preview(showSystemUi = true)
@Composable
fun AmphibianCardPreview() {
    AmphibiansTheme(dynamicColor = false) {
        AmphibianCard(
            Amphibian(
                "Great Basin Spadefoot",
                "Toad",
                "This toad spends most of its life underground due to the arid desert conditions in which it lives. Spadefoot toads earn the name because of their hind legs which are wedged to aid in digging. They are typically grey, green, or brown with dark spots.",
                "https://developer.android.com/codelabs/basic-android-kotlin-compose-amphibians-app/img/great-basin-spadefoot.png"
            ),
            modifier = Modifier.safeDrawingPadding()
        )
    }
}