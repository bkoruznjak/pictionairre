package hr.flowable.pictionairre

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MainActivity : AppCompatActivity() {

  private var state: State by mutableStateOf(State(screen = Screen.Home()))

  @Deprecated("Deprecated in Java")
  override fun onBackPressed() {
    state.reduce(Event.BackButtonClicked)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    snapshotFlow { state }
      .onEach { handleSideEffects(it) }
      .launchIn(CoroutineScope(Dispatchers.Main))

    setContent {
      MaterialTheme {
        when (val screen = state.screen) {
          is Screen.CategoryOverview -> CategoryOverview(
            state = screen,
            onEvent = { state.reduce(it) }
          )
          is Screen.DrawingTimer     -> DrawingTimer(
            state = screen,
            onEvent = { state.reduce(it) }
          )
          is Screen.Home             -> HomeScreen(
            state = screen,
            onEvent = { state.reduce(it) })
          else                       -> Column(modifier = Modifier.fillMaxSize()) {}
        }
      }
    }
  }

  private fun handleSideEffects(state: State) =
    when (state.screen) {
      Screen.Closing -> finish()
      else           -> Unit
    }

  private fun State.reduce(event: Event) {
    this@MainActivity.state = reduce(this, event)
  }
}
