package hr.flowable.pictionairre

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CategoryItem(
  categoryName: String,
  color: Color,
  modifier: Modifier = Modifier
) =
  Box(
    modifier = modifier
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

@Composable
fun ToolbarButton(onClick: () -> Unit, modifier: Modifier = Modifier, @DrawableRes iconRes: Int) =
  Box(modifier = Modifier
    .clickable { onClick() }
    .size(64.dp),
    contentAlignment = Alignment.Center
  ) {
    Icon(painterResource(id = iconRes), contentDescription = null)
  }


