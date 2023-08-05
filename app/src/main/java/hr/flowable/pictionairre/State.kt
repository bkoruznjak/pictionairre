package hr.flowable.pictionairre

import androidx.compose.ui.graphics.Color
import hr.flowable.pictionairre.Event.BackButtonClicked
import hr.flowable.pictionairre.Event.CategoryClicked
import hr.flowable.pictionairre.NavigationIntent.GoBack
import hr.flowable.pictionairre.NavigationIntent.GoToCategoryOverview
import hr.flowable.pictionairre.PictColors.PBlue
import hr.flowable.pictionairre.PictColors.PGreen
import hr.flowable.pictionairre.PictColors.POrange
import hr.flowable.pictionairre.PictColors.PRed
import hr.flowable.pictionairre.PictColors.PYellow

data class State(
  val screen: Screen,
  val navigationIntent: NavigationIntent? = null
)

sealed interface Event {
  object BackButtonClicked : Event
  data class CategoryClicked(val category: WordCategory) : Event
}

sealed interface Screen {
  data class Home(val categories: Set<WordCategory> = regularCategories()) : Screen
  object CategoryOverview : Screen
  object DrawingTimer : Screen
}

sealed interface NavigationIntent {
  object GoBack : NavigationIntent
  data class GoToCategoryOverview(val category: WordCategory) : NavigationIntent
  data class GoToDrawingTimer(val word: String) : NavigationIntent
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
    BackButtonClicked -> state.copy(navigationIntent = GoBack)
    is CategoryClicked -> state.copy(navigationIntent = GoToCategoryOverview(event.category)
    )
  }
