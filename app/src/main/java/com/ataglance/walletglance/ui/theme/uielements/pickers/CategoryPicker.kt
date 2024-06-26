package com.ataglance.walletglance.ui.theme.uielements.pickers

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ataglance.walletglance.R
import com.ataglance.walletglance.data.categories.CategoriesLists
import com.ataglance.walletglance.data.categories.Category
import com.ataglance.walletglance.data.categories.CategoryType
import com.ataglance.walletglance.ui.theme.GlanceTheme
import com.ataglance.walletglance.ui.theme.WindowTypeIsCompact
import com.ataglance.walletglance.ui.theme.WindowTypeIsMedium
import com.ataglance.walletglance.ui.theme.animation.bounceClickEffect
import com.ataglance.walletglance.ui.theme.animation.dialogSlideFromBottomTransition
import com.ataglance.walletglance.ui.theme.animation.dialogSlideToBottomTransition
import com.ataglance.walletglance.ui.theme.uielements.buttons.CloseButton
import com.ataglance.walletglance.ui.theme.uielements.dividers.SmallDivider

@Composable
fun CategoryPicker(
    visible: Boolean,
    categoriesUiState: CategoriesLists,
    type: CategoryType,
    onDismissRequest: () -> Unit,
    onCategoryChoose: (Category, Category?) -> Unit
) {
    val parentCategoryListState = rememberLazyListState()
    val subcategoryListState = rememberLazyListState()
    var showSubcategoryList by remember { mutableStateOf(false) }
    var chosenCategory by remember {
        mutableStateOf(categoriesUiState.parentCategories.expense.first())
    }

    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(tween(400)),
        exit = fadeOut(tween(400))
    ) {
        Box(
            modifier = Modifier
                .clickable {
                    onDismissRequest()
                    if (showSubcategoryList) showSubcategoryList = false
                }
                .fillMaxSize()
                .background(Color.Black.copy(.3f))
        )
    }
    AnimatedVisibility(
        visible = visible && !showSubcategoryList,
        enter = dialogSlideFromBottomTransition,
        exit = dialogSlideToBottomTransition
    ) {
        CategoryListWindow(
            lazyListState = parentCategoryListState,
            list = if (type == CategoryType.Expense) {
                categoriesUiState.parentCategories.expense
            } else {
                categoriesUiState.parentCategories.income
            },
            onCategoryClick = { category ->
                if (category.parentCategoryId != null) {
                    chosenCategory = category
                    showSubcategoryList = true
                } else {
                    onCategoryChoose(category, null)
                    onDismissRequest()
                }
            }
        )
    }
    AnimatedVisibility(
        visible = visible && showSubcategoryList,
        enter = dialogSlideFromBottomTransition,
        exit = dialogSlideToBottomTransition
    ) {
        CategoryListWindow(
            lazyListState = subcategoryListState,
            list = if (type == CategoryType.Expense) {
                categoriesUiState.subcategories.expense[chosenCategory.orderNum - 1]
            } else {
                categoriesUiState.subcategories.income[chosenCategory.orderNum - 1]
            },
            onCategoryClick = { category ->
                onCategoryChoose(chosenCategory, category)
                onDismissRequest()
                showSubcategoryList = false
            },
            showCloseButton = true,
            onCloseSubcategoryList = {
                showSubcategoryList = false
            }
        )
    }
}

@Composable
private fun CategoryListWindow(
    lazyListState: LazyListState,
    list: List<Category>,
    onCategoryClick: (Category) -> Unit,
    showCloseButton: Boolean = false,
    onCloseSubcategoryList: () -> Unit = {}
) {
    LazyColumn(
        state = lazyListState,
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(top = 84.dp, bottom = dimensionResource(R.dimen.screen_vertical_padding))
            .clip(RoundedCornerShape(dimensionResource(R.dimen.dialog_corner_size)))
            .background(GlanceTheme.surfaceVariant.copy(1f))
            .fillMaxWidth(
                when {
                    WindowTypeIsCompact -> .9f
                    WindowTypeIsMedium -> .6f
                    else -> .5f
                }
            )
    ) {
        items(
            items = list,
            key = { it.id }
        ) { category ->
            if (category.orderNum != 1) {
                SmallDivider()
            }
            CategoryListItem(category, onCategoryClick)
        }
        if (showCloseButton) {
            item {
                SmallDivider(filledWidth = .6f)
                CloseButton(onClick = onCloseSubcategoryList)
            }
        }
    }
}

@Composable
private fun CategoryListItem(
    category: Category,
    onClick: (Category) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier
            .bounceClickEffect { onClick(category) }
            .padding(vertical = 14.dp)
    ) {
        category.icon.res.let {
            Icon(
                painter = painterResource(it),
                contentDescription = category.name + " icon",
                tint = GlanceTheme.onSurface,
                modifier = Modifier
                    .size(25.dp)
            )
        }
        Text(
            text = category.name,
            color = GlanceTheme.onSurface,
            fontSize = 18.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}