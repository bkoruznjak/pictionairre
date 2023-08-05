package hr.flowable.pictionairre

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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
  Column(
    modifier = modifier
      .verticalScroll(rememberScrollState())
      .padding(16.dp)
  ) {
    state.categories.forEach {
      CategoryButton(
        categoryName = stringResource(id = it.nameResource),
        color = it.color,
        onClick = { onEvent(Event.CategoryClicked(it)) })

      Spacer(modifier = Modifier.height(48.dp))
    }
  }

@Composable
private fun CategoryButton(
  categoryName: String,
  color: Color,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) =
  Box(modifier = modifier
    .clickable { onClick() }
    .fillMaxWidth()
    .heightIn(min = 96.dp)
    .background(color = color, shape = RoundedCornerShape(12.dp))
    .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(12.dp))
    .padding(16.dp),
    contentAlignment = Alignment.CenterStart
  ) {
    Text(
      categoryName.uppercase(),
      style = TextStyle(color = Color.Black, fontSize = 24.sp, fontWeight = FontWeight.Bold)
    )
  }


