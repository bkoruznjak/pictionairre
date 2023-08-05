package hr.flowable.pictionairre

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
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
          Screen.CategoryOverview -> CategoryOverview()
          Screen.DrawingTimer     -> CategoryOverview()
          is Screen.Home          -> HomeScreen(
            state = screen,
            onEvent = { state.reduce(it) })
        }
      }
    }
  }

  private fun handleSideEffects(state: State) {
    this.state = when (state.navigationIntent) {
      NavigationIntent.GoBack -> when (state.screen) {
        Screen.CategoryOverview -> state.copy(screen = Screen.Home(), navigationIntent = null)
        Screen.DrawingTimer     -> state.copy(
          screen = Screen.CategoryOverview,
          navigationIntent = null
        )
        is Screen.Home          -> state.copy(screen = Screen.Home(), navigationIntent = null)
          .also { finish() }
      }
      is NavigationIntent.GoToCategoryOverview -> state.copy(
        screen = Screen.CategoryOverview,
        navigationIntent = null
      )
      is NavigationIntent.GoToDrawingTimer -> state.copy(
        screen = Screen.DrawingTimer,
        navigationIntent = null
      )
      else -> state
    }
  }

  private fun State.reduce(event: Event) {
    this@MainActivity.state = reduce(this, event)
  }
}
