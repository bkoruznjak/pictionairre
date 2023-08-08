package hr.flowable.pictionairre

import androidx.annotation.RawRes
import androidx.compose.ui.graphics.Color
import hr.flowable.pictionairre.Event.BackButtonClicked
import hr.flowable.pictionairre.Event.CategoryClicked
import hr.flowable.pictionairre.PictColors.PBlue
import hr.flowable.pictionairre.PictColors.PGreen
import hr.flowable.pictionairre.PictColors.POrange
import hr.flowable.pictionairre.PictColors.PRed
import hr.flowable.pictionairre.PictColors.PYellow

private const val DRAWING_TIME_IN_SECONDS = 60L

data class State(
  val screen: Screen,
  val soundEffect: SoundEffect? = null
)

sealed interface SoundEffect {

  val resource: Int

  @JvmInline
  value class WarningShort(@RawRes override val resource: Int = R.raw.crosswalk) : SoundEffect

  @JvmInline
  value class TimeOut(@RawRes override val resource: Int = R.raw.finale) : SoundEffect

  @JvmInline
  value class TimeOutSad(@RawRes override val resource: Int = R.raw.sad) : SoundEffect
}

sealed interface Event {
  object BackButtonClicked : Event
  data class CategoryClicked(val category: WordCategory) : Event
  data class RefreshCategoryClicked(val category: WordCategory) : Event
  data class GoButtonClicked(val category: WordCategory) : Event
  data class CategoryWordClicked(val word: String) : Event
  object RemoveSoundEffect : Event
  data class SecondExpired(val remainingTime: Long) : Event
}

sealed interface Screen {
  data class Home(val categories: Set<WordCategory> = regularCategories()) : Screen

  data class CategoryOverview(
    val category: WordCategory,
    val selectedWord: String,
    val words: List<String>,
  ) : Screen

  data class DrawingTimer(
    val category: WordCategory,
    val selectedWord: String,
    val timeRemainingInSeconds: Long,
    val words: List<String>,
  ) : Screen

  object Closing : Screen
}

sealed class WordCategory {
  abstract val color: Color
  abstract val nameResource: Int

  data class Objects(
    override val color: Color,
    override val nameResource: Int
  ) : WordCategory()

  data class PeoplePlacesAnimals(
    override val color: Color,
    override val nameResource: Int
  ) : WordCategory()

  data class Actions(
    override val color: Color,
    override val nameResource: Int
  ) : WordCategory()

  data class Hard(
    override val color: Color,
    override val nameResource: Int
  ) : WordCategory()

  data class All(
    override val color: Color,
    override val nameResource: Int
  ) : WordCategory()
}

fun regularCategories() = setOf(
  WordCategory.Objects(color = PYellow, nameResource = R.string.category_yellow),
  WordCategory.PeoplePlacesAnimals(color = PBlue, nameResource = R.string.category_blue),
  WordCategory.Actions(color = POrange, nameResource = R.string.category_orange),
  WordCategory.Hard(color = PGreen, nameResource = R.string.category_green),
  WordCategory.All(color = PRed, nameResource = R.string.category_red),
)

fun reduce(state: State, event: Event): State =
  when (event) {
    BackButtonClicked               -> when (state.screen) {
      is Screen.CategoryOverview -> state.copy(screen = Screen.Home())
      is Screen.DrawingTimer     -> state.copy(screen = Screen.Home())
      is Screen.Home             -> state.copy(screen = Screen.Closing)
      Screen.Closing             -> state
    }
    is CategoryClicked              -> state.copy(
      screen =
      Screen.CategoryOverview(
        category = event.category,
        words = PictApp.dataStore.getWordPair(event.category),
        selectedWord = ""
      )
    )
    is Event.CategoryWordClicked    -> state.copy(
      screen = (state.screen as? Screen.CategoryOverview)?.copy(
        selectedWord = event.word
      ) ?: state.screen
    )
    is Event.RefreshCategoryClicked -> state.copy(
      screen = (state.screen as? Screen.CategoryOverview)?.copy(
        words = PictApp.dataStore.getWordPair(event.category),
        selectedWord = ""
      ) ?: state.screen
    )
    is Event.GoButtonClicked        -> state.copy(
      screen = (state.screen as? Screen.CategoryOverview)?.let { categoryOverview ->
        Screen.DrawingTimer(
          category = categoryOverview.category,
          words = categoryOverview.words,
          selectedWord = categoryOverview.selectedWord,
          timeRemainingInSeconds = DRAWING_TIME_IN_SECONDS
        )
      } ?: state.screen
    )
    is Event.RemoveSoundEffect      -> state.copy(soundEffect = null)
    is Event.SecondExpired          -> when (event.remainingTime) {
      0L                          -> state.copy(soundEffect = SoundEffect.TimeOutSad())
      DRAWING_TIME_IN_SECONDS / 2 -> state.copy(soundEffect = SoundEffect.WarningShort())
      else                        -> state
    }
  }
