package com.ataglance.walletglance.ui.theme.uielements.categories

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ataglance.walletglance.data.categories.Category
import com.ataglance.walletglance.ui.theme.GlanceTheme

@Composable
fun RecordCategory(
    category: Category?,
    iconSize: Dp = 22.dp,
    fontSize: TextUnit = 18.sp
) {
    category?.let {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(category.icon.res),
                contentDescription = category.name + " icon",
                tint = GlanceTheme.onSurface,
                modifier = Modifier.size(iconSize)
            )
            Text(
                text = category.name,
                color = GlanceTheme.onSurface,
                fontSize = fontSize,
                fontWeight = FontWeight.Light,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f, false)
            )
        }
    }
}