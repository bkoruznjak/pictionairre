package hr.flowable.pictionairre

import android.content.Context

private const val WORD_COUNT_PER_ROUND = 2
private const val PREF_KEY_YELLOW = "Yellow"
private const val PREF_KEY_BLUE = "Blue"
private const val PREF_KEY_ORANGE = "Orange"
private const val PREF_KEY_GREEN = "Green"
private const val PREF_KEY_RED = "Red"

class DataStore(context: Context) {
  private val sharedPrefs = context.getSharedPreferences(
    context.getString(R.string.app_name_shared_prefs), Context.MODE_PRIVATE
  )

  fun markWordsShown(
    words: List<String>,
    category: WordCategory
  ) {
    val key = category.toPrefKey()
    val editable = sharedPrefs.edit()
    val currentSeenSet = sharedPrefs.getStringSet(key, emptySet()) ?: emptySet()
    val combined = words.toSet().union(currentSeenSet)
    editable.putStringSet(key, combined)
    editable.apply()
  }

  fun getWordPair(category: WordCategory): List<String> {
    val existingWordsInCategory: List<String> = when (category) {
      is WordCategory.Actions             -> Data.OrangeTerms
      is WordCategory.All                 -> Data.RedTerms
      is WordCategory.Hard                -> Data.GreenTerms
      is WordCategory.Objects             -> Data.YellowTerms
      is WordCategory.PeoplePlacesAnimals -> Data.BlueTerms
    }.shuffled()

    val prefKey = category.toPrefKey()
    val allSeenWordsInCategory: Set<String> =
      sharedPrefs.getStringSet(prefKey, emptySet()) ?: emptySet()

    val notSeenWords: Set<String> = existingWordsInCategory.subtract(allSeenWordsInCategory)

    val twoWords = if (notSeenWords.size < WORD_COUNT_PER_ROUND) {
      //remove the seen words and take two from the original shuffled deck
      sharedPrefs.edit().remove(prefKey).apply()
      existingWordsInCategory.take(WORD_COUNT_PER_ROUND)
    } else {
      notSeenWords.take(WORD_COUNT_PER_ROUND)
    }

    markWordsShown(twoWords, category)
    return twoWords
  }

  private fun WordCategory.toPrefKey() = when (this) {
    is WordCategory.Actions             -> PREF_KEY_ORANGE
    is WordCategory.All                 -> PREF_KEY_RED
    is WordCategory.Hard                -> PREF_KEY_GREEN
    is WordCategory.Objects             -> PREF_KEY_YELLOW
    is WordCategory.PeoplePlacesAnimals -> PREF_KEY_BLUE
  }
}
