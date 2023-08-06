package hr.flowable.pictionairre

import androidx.compose.foundation.background
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
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun DrawingTimer(
  state: Screen.DrawingTimer,
  onEvent: (Event) -> Unit,
  modifier: Modifier = Modifier
) {

  var remainingTime by remember { mutableStateOf(state.timeRemainingInSeconds) }
  var isTimerRunning by remember { mutableStateOf(true) }

  BackgroundWithImage {
    Column(
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
      }

      Spacer(modifier = Modifier.height(16.dp))
      // category name
      CategoryItem(
        categoryName = stringResource(id = state.category.nameResource),
        color = state.category.color
      )
      Spacer(modifier = Modifier.weight(1f))
      // word one

      if (remainingTime == 0L) {
        Text(
          text = state.selectedWord,
          style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 24.sp)
        )

        Spacer(modifier = Modifier.height(16.dp))
      }

      Row(modifier = Modifier.fillMaxWidth()) {
        val wordsInWord = state.selectedWord.split(" ")
        repeat(wordsInWord.size) {
          Box(
            modifier = Modifier
              .padding(horizontal = 8.dp)
              .background(color = Color.Gray, shape = RoundedCornerShape(8.dp))
              .heightIn(min = 8.dp)
              .weight(1f)
          )
        }
      }

      Spacer(modifier = Modifier.height(32.dp))
      if (remainingTime != 0L) {
        Text(
          text = formatTimeToMinutesAndSeconds(remainingTime), style =
          TextStyle(fontWeight = FontWeight.Bold, fontSize = 32.sp)
        )
      }

      // go button
      Spacer(modifier = Modifier.weight(1f))
      Button(
        onClick = {
          if (remainingTime != 0L) {
            isTimerRunning = !isTimerRunning
          } else {
            onEvent(Event.BackButtonClicked)
          }
        },
        modifier = Modifier
          .fillMaxWidth()
          .height(56.dp),
        enabled = state.selectedWord.isNotBlank()
      ) {
        Text(
          stringResource(
            id =
            when {
              remainingTime == 0L -> R.string.button_finish
              isTimerRunning      -> R.string.button_stop
              else                -> R.string.button_start
            }
          ),
          style = TextStyle(color = Color.White, fontWeight = FontWeight.Bold, fontSize = 24.sp)
        )
      }
      Spacer(modifier = Modifier.height(16.dp))
    }

    LaunchedEffect(key1 = remainingTime, key2 = isTimerRunning) {
      when {
        isTimerRunning && remainingTime > 0L -> {
          delay(1000L)
          remainingTime = remainingTime.dec()
        }
      }
    }
  }

}

private fun formatTimeToMinutesAndSeconds(remaining: Long): String {
  val minutes = remaining / 60
  val remainingSeconds = remaining % 60
  return String.format("%02d:%02d", minutes, remainingSeconds)
}

