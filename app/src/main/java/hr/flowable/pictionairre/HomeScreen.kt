package hr.flowable.pictionairre

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
@Preview
private fun HomeScreenPreview() {
  HomeScreen(Screen.Home(categories = regularCategories()), onEvent = {})
}

@Composable
fun HomeScreen(
  state: Screen.Home,
  onEvent: (Event) -> Unit,
  modifier: Modifier = Modifier
) =
  BackgroundWithImage {
    Column(
      modifier = modifier
        .verticalScroll(rememberScrollState())
        .padding(16.dp)
    ) {
      state.categories.forEach {
        CategoryItem(
          categoryName = stringResource(id = it.nameResource),
          color = it.color,
          modifier = Modifier.clickable { onEvent(Event.CategoryClicked(it)) })

        Spacer(modifier = Modifier.height(48.dp))
      }
    }
  }

