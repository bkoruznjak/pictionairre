package hr.flowable.pictionairre

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun CategoryOverview(modifier : Modifier = Modifier) = Column(
  modifier = modifier.fillMaxSize().background(color = PictColors.POrange)
){}
