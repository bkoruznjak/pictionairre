package hr.flowable.pictionairre

import android.app.Application

class PictApp : Application() {

  override fun onCreate() {
    super.onCreate()
    INSTANCE = this
  }

  companion object {
    lateinit var INSTANCE: PictApp

    val dataStore: DataStore by lazy { DataStore(context = INSTANCE) }
  }
}
