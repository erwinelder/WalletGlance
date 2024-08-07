package com.ataglance.walletglance.ui.theme.uielements.records

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ataglance.walletglance.data.app.AppTheme
import com.ataglance.walletglance.data.records.RecordStack
import com.ataglance.walletglance.ui.theme.GlanceTheme
import com.ataglance.walletglance.ui.theme.uielements.categories.RecordCategory
import com.ataglance.walletglance.ui.theme.uielements.containers.GlassSurfaceOnGlassSurface
import com.ataglance.walletglance.data.utils.convertDateLongToDayMonthYear

@Composable
fun RecordStackComponent(
    appTheme: AppTheme?,
    recordStack: RecordStack,
    includeYearToDate: Boolean,
    onRecordClick: (Int) -> Unit
) {
    GlassSurfaceOnGlassSurface(onClick = { onRecordClick(recordStack.recordNum) }) {
        // date
        Text(
            text = convertDateLongToDayMonthYear(
                date = recordStack.date,
                context = LocalContext.current,
                includeYear = includeYearToDate
            ),
            color = GlanceTheme.outline,
            fontSize = 16.sp
        )
        // note with category
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(2.dp),
        ) {
            recordStack.stack.take(3).forEach { recordStackUnit ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (!recordStackUnit.note.isNullOrEmpty()) {
                        Text(
                            text = recordStackUnit.note,
                            color = GlanceTheme.onSurface,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Light,
                            fontStyle = FontStyle.Italic,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    RecordCategory(
                        category = recordStackUnit.let {
                            it.categoryWithSubcategory?.getSubcategoryOrCategory()
                        },
                        appTheme = appTheme,
                    )
                }
            }
        }
        // amount
        Text(
            text = recordStack.getFormattedAmountWithSpaces(),
            color = GlanceTheme.onSurface,
            fontSize = 20.sp,
            fontWeight = FontWeight.Light
        )
    }
}