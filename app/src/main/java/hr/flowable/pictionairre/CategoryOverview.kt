package hr.flowable.pictionairre

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
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
private fun CategoryOverviewPreview() {
  MaterialTheme {
    CategoryOverview(
      state = Screen.CategoryOverview(
        category = WordCategory.Objects(
          PictColors.PYellow,
          R.string.category_yellow
        ),
        words = listOf("Rengo", "Yuumi"),
        selectedWord = "Rengo"
      ),
      onEvent = {})
  }
}

@Composable
fun CategoryOverview(
  state: Screen.CategoryOverview,
  onEvent: (Event) -> Unit,
  modifier: Modifier = Modifier
) = Column(
  modifier = modifier
    .fillMaxSize()
    .padding(horizontal = 16.dp),
  horizontalAlignment = Alignment.CenterHorizontally
) {
  //go back + refresh button
  Row(modifier = Modifier.fillMaxWidth()) {
    ToolbarButton(
      onClick = { onEvent(Event.BackButtonClicked) },
      iconRes = R.drawable.ic_back
    )

    Spacer(modifier.weight(1f))

    ToolbarButton(
      onClick = { onEvent(Event.RefreshCategoryClicked(state.category)) },
      iconRes = R.drawable.ic_refresh
    )
  }

  Spacer(modifier = Modifier.height(16.dp))
  // category name
  CategoryItem(
    categoryName = stringResource(id = state.category.nameResource),
    color = state.category.color
  )
  Spacer(modifier = Modifier.weight(1f))
  // word one

  state.words.forEachIndexed { index, word ->
    WordSelectable(
      word,
      isSelected = word.equals(state.selectedWord, ignoreCase = true),
      onClick = { onEvent(Event.CategoryWordClicked(word)) }
    )
    Spacer(modifier = Modifier.height(16.dp))

    if (index < state.words.lastIndex) {
      Text(
        stringResource(id = R.string.word_or),
        style = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 24.sp)
      )
      Spacer(modifier = Modifier.height(16.dp))
    }
  }

  // go button
  Spacer(modifier = Modifier.weight(1f))
  Button(
    onClick = { onEvent(Event.GoButtonClicked(category = state.category)) },
    modifier = Modifier
      .fillMaxWidth()
      .height(56.dp),
    enabled = state.selectedWord.isNotBlank()
  ) {
    Text(
      stringResource(id = R.string.button_go),
      style = TextStyle(color = Color.White, fontWeight = FontWeight.Bold, fontSize = 24.sp)
    )
  }
  Spacer(modifier = Modifier.height(16.dp))
}

@Composable
private fun WordSelectable(
  word: String,
  isSelected: Boolean,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) = Box(
  modifier = modifier
    .clickable { onClick() }
    .fillMaxWidth()
    .heightIn(min = 96.dp)
    .background(
      color = if (isSelected) MaterialTheme.colors.primary.copy(alpha = 0.2f) else Color.White,
      shape = RoundedCornerShape(16.dp)
    )
    .border(2.dp, Color.Black, shape = RoundedCornerShape(16.dp)),
  contentAlignment = Alignment.Center
) {
  Text(
    word,
    style = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 24.sp)
  )
}
