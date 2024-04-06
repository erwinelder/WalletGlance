package com.ataglance.walletglance.ui.theme.screens.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.ataglance.walletglance.R
import com.ataglance.walletglance.data.Category
import com.ataglance.walletglance.model.Colors
import com.ataglance.walletglance.ui.theme.uielements.buttons.PrimaryButton
import com.ataglance.walletglance.ui.theme.uielements.containers.EditSubcategoryListContainer

@Composable
fun EditSubcategoryListScreen(
    scaffoldPadding: PaddingValues,
    subcategoryList: List<Category>,
    categoryNameAndIconMap: Map<String, Int>,
    categoryColorNameToColorMap: Map<String, Colors>,
    onSaveButton: () -> Unit,
    onNavigateToEditCategoryScreen: (Int) -> Unit,
    onSwapCategories: (Int, Int) -> Unit,
    onAddNewSubcategory: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = scaffoldPadding.calculateTopPadding() + dimensionResource(R.dimen.screen_vertical_padding),
                bottom = dimensionResource(R.dimen.screen_vertical_padding)
            )
    ) {
        EditSubcategoryListContainer(
            subcategoryList = subcategoryList,
            categoryNameAndIconMap = categoryNameAndIconMap,
            categoryColorNameToColorMap = categoryColorNameToColorMap,
            onNavigateToEditCategoryScreen = onNavigateToEditCategoryScreen,
            onSwapCategories = onSwapCategories,
            onAddNewSubcategory = onAddNewSubcategory
        )
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.widgets_gap)))
        PrimaryButton(
            onClick = onSaveButton,
            text = stringResource(R.string.save)
        )
    }
}