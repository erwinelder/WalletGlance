package com.ataglance.walletglance.ui.theme.uielements.pickers

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.ataglance.walletglance.R
import com.ataglance.walletglance.ui.theme.GlanceTheme
import com.ataglance.walletglance.ui.theme.WalletGlanceTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDatePicker(
    openDialog: Boolean,
    onOpenDateDialogChange: (Boolean) -> Unit,
    onConfirmButton: () -> Unit,
    state: DatePickerState
) {
    AnimatedVisibility(visible = openDialog) {
        DatePickerDialog(
            onDismissRequest = { onOpenDateDialogChange(false) },
            confirmButton = {
                TextButton(onClick = onConfirmButton) {
                    Text(text = stringResource(R.string.confirm))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { onOpenDateDialogChange(false) }
                ) {
                    Text(text = stringResource(R.string.cancel))
                }
            }
        ) {
            DatePicker(
                state = state,
                colors = DatePickerDefaults.colors(
                    containerColor = GlanceTheme.surface,
                    titleContentColor = GlanceTheme.onSurface,
                    headlineContentColor = GlanceTheme.onSurface,
                    weekdayContentColor = GlanceTheme.onSurface,
                    subheadContentColor = GlanceTheme.onSurface,
                    navigationContentColor = GlanceTheme.onSurface,
                    yearContentColor = GlanceTheme.onSurface,
                    disabledYearContentColor = GlanceTheme.onSurface,
                    currentYearContentColor = GlanceTheme.primary,
                    selectedYearContentColor = GlanceTheme.onPrimary,
                    disabledSelectedYearContentColor = GlanceTheme.onSurface,
                    selectedYearContainerColor = GlanceTheme.primary,
                    disabledSelectedYearContainerColor = GlanceTheme.onSurface,
                    dayContentColor = GlanceTheme.onSurface,
                    disabledDayContentColor = GlanceTheme.onSurface,
                    selectedDayContentColor = GlanceTheme.onPrimary,
                    disabledSelectedDayContentColor = GlanceTheme.onSurface,
                    selectedDayContainerColor = GlanceTheme.primary,
                    disabledSelectedDayContainerColor = GlanceTheme.onSurface,
                    todayContentColor = GlanceTheme.primary,
                    todayDateBorderColor = GlanceTheme.primary,
                    dayInSelectionRangeContentColor = GlanceTheme.onSurface,
                    dayInSelectionRangeContainerColor = GlanceTheme.onSurface,
                    dividerColor = GlanceTheme.outline,
                    dateTextFieldColors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = GlanceTheme.onSurface,
                        unfocusedIndicatorColor = GlanceTheme.outline,
                        focusedLabelColor = GlanceTheme.onSurface,
                        unfocusedLabelColor = GlanceTheme.onSurface,
                        disabledLabelColor = GlanceTheme.onSurface
                    )
                )
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun CustomDatePickerPreview() {
    BoxWithConstraints {
        WalletGlanceTheme(
            boxWithConstraintsScope = this
        ) {
            val datePickerState = rememberDatePickerState(
                initialSelectedDateMillis = System.currentTimeMillis(),
                initialDisplayMode = DisplayMode.Picker
            )
            CustomDatePicker(
                openDialog = true,
                onOpenDateDialogChange = {},
                onConfirmButton = {},
                state = datePickerState
            )
        }
    }
}