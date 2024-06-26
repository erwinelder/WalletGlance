package com.ataglance.walletglance.ui.theme.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ataglance.walletglance.R
import com.ataglance.walletglance.data.accounts.Account
import com.ataglance.walletglance.data.app.AppTheme
import com.ataglance.walletglance.data.date.DateRangeEnum
import com.ataglance.walletglance.data.records.RecordStack
import com.ataglance.walletglance.data.records.RecordsTypeFilter
import com.ataglance.walletglance.ui.theme.screencontainers.ScreenDataContainer
import com.ataglance.walletglance.ui.theme.uielements.records.RecordStackComponent
import com.ataglance.walletglance.ui.theme.uielements.records.RecordsTypeFilterBar
import com.ataglance.walletglance.ui.theme.uielements.records.TransferComponent
import com.ataglance.walletglance.ui.utils.findById
import com.ataglance.walletglance.ui.utils.containsRecordsFromDifferentYears

@Composable
fun RecordsScreen(
    scaffoldAppScreenPadding: PaddingValues,
    appTheme: AppTheme?,
    accountList: List<Account>,
    recordStackList: List<RecordStack>,
    onAccountClick: (Int) -> Unit,
    currentDateRangeEnum: DateRangeEnum,
    isCustomDateRangeWindowOpened: Boolean,
    onDateRangeChange: (DateRangeEnum) -> Unit,
    onCustomDateRangeButtonClick: () -> Unit,
    onRecordClick: (Int) -> Unit,
    onTransferClick: (Int) -> Unit
) {
    var recordsType by remember { mutableStateOf(RecordsTypeFilter.All) }
    val lazyListState = rememberLazyListState()
    val filteredRecordStackList = when (recordsType) {
        RecordsTypeFilter.All -> recordStackList
        RecordsTypeFilter.Expenses -> recordStackList.filter { it.isExpenseOrOutTransfer() }
        RecordsTypeFilter.Income -> recordStackList.filter { it.isIncomeOrInTransfer() }
    }
    val includeYearToRecordDate = recordStackList.containsRecordsFromDifferentYears()

    ScreenDataContainer(
        scaffoldAppScreenPadding = scaffoldAppScreenPadding,
        accountList = accountList,
        appTheme = appTheme,
        onAccountClick = onAccountClick,
        currentDateRangeEnum = currentDateRangeEnum,
        isCustomDateRangeWindowOpened = isCustomDateRangeWindowOpened,
        onDateRangeChange = onDateRangeChange,
        onCustomDateRangeButtonClick = onCustomDateRangeButtonClick,
        animationContentLabel = "records history widget content",
        animatedContentTargetState = Pair(filteredRecordStackList, recordsType),
        visibleNoDataMessage = filteredRecordStackList.isEmpty(),
        noDataMessageResource = when(recordsType) {
            RecordsTypeFilter.All -> R.string.you_have_no_records_in_date_range
            RecordsTypeFilter.Expenses -> R.string.you_have_no_expenses_in_date_range
            RecordsTypeFilter.Income -> R.string.you_have_no_income_in_date_range
        },
        typeFilterBar = {
            RecordsTypeFilterBar(recordsType) {
                recordsType = it
            }
        },
    ) { targetRecordStackListAndTypeFilter ->
        LazyColumn(
            state = lazyListState,
            contentPadding = PaddingValues(vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(
                items = targetRecordStackListAndTypeFilter.first,
                key = { it.recordNum }
            ) { recordStack ->
                if (recordStack.isTransfer()) {
                    TransferComponent(
                        recordStack = recordStack,
                        includeYearToDate = includeYearToRecordDate,
                        appTheme = appTheme,
                        secondAccount = recordStack.stack.firstOrNull()?.note?.toInt()?.let {
                            accountList.findById(it)?.toRecordAccount()
                        },
                        onTransferClick = onTransferClick
                    )
                } else {
                    RecordStackComponent(
                        recordStack = recordStack,
                        includeYearToDate = includeYearToRecordDate,
                        onRecordClick = onRecordClick
                    )
                }
            }
        }
    }
}