package nl.mranderson.mrnews.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import nl.mranderson.mrnews.ui.theme.news.NewsTheme

@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    HomeScreen(viewData = state)
}

@Composable
fun HomeScreen(
    viewData: HomeViewModel.ScreenState,
) {
    val scrollState = rememberScrollState()
    Scaffold { innerPadding ->
        when (viewData) {
            is HomeViewModel.ScreenState.Data -> {
                Column(
                    modifier = Modifier
                        .consumeWindowInsets(innerPadding)
                        .padding(16.dp)
                        .verticalScroll(scrollState),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    viewData.newsItems.forEachIndexed { index, item ->
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            AsyncImage(
                                modifier = Modifier
                                    .height(90.dp)
                                    .width(110.dp)
                                    .clip(
                                        RoundedCornerShape(10.dp)
                                    ),
                                contentScale = ContentScale.Crop,
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(item.image)
                                    .crossfade(true)
                                    .build(),
                                contentDescription = null,
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                modifier = Modifier.fillMaxSize(),
                                text = item.title,
                                style = TextStyle(fontWeight = FontWeight.Bold)
                            )
                        }
                        if (index < viewData.newsItems.lastIndex)
                            HorizontalDivider(color = Color.Black, thickness = 1.dp)
                    }
                    Button(onClick = { /*TODO*/ }) {
                        Text(text = "Button")
                    }
                }
            }

            HomeViewModel.ScreenState.Error -> TODO()
            HomeViewModel.ScreenState.Loading -> {
                Column(
                    modifier = Modifier
                        .consumeWindowInsets(innerPadding)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}
