package com.ataglance.walletglance.data.makingRecord

import com.ataglance.walletglance.data.accounts.Account
import com.ataglance.walletglance.data.date.DateTimeState
import com.ataglance.walletglance.data.records.RecordStack
import com.ataglance.walletglance.data.records.RecordType
import com.ataglance.walletglance.domain.entities.Record
import java.util.Locale

data class MakeRecordUiState(
    val recordStatus: MakeRecordStatus,
    val recordNum: Int,
    val account: Account?,
    val type: RecordType = RecordType.Expense,
    val clickedUnitIndex: Int = 0,
    val dateTimeState: DateTimeState = DateTimeState()
) {

    fun toRecordList(unitList: List<MakeRecordUnitUiState>): List<Record> {
        val recordList = mutableListOf<Record>()

        unitList.forEach { unit ->
            if (account != null && unit.categoryWithSubcategory != null) {
                recordList.add(
                    Record(
                        recordNum = recordNum,
                        date = dateTimeState.dateLong,
                        type = if (type == RecordType.Expense) '-' else '+',
                        amount = if (unit.quantity.isNotBlank()) {
                            "%.2f".format(
                                Locale.US,
                                unit.amount.toDouble() * unit.quantity.toInt()
                            ).toDouble()
                        } else {
                            unit.amount.toDouble()
                        },
                        quantity = unit.quantity.ifBlank { null }?.toInt(),
                        categoryId = unit.categoryWithSubcategory.category.id,
                        subcategoryId = unit.categoryWithSubcategory.subcategory?.id,
                        accountId = account.id,
                        note = unit.note.ifBlank { null }
                    )
                )
            }
        }

        return recordList
    }

    fun toRecordListWithOldIds(
        unitList: List<MakeRecordUnitUiState>,
        recordStack: RecordStack
    ): List<Record> {
        val recordList = mutableListOf<Record>()

        unitList.forEach { unit ->
            if (account != null && unit.categoryWithSubcategory != null) {
                recordList.add(
                    Record(
                        id = recordStack.stack[unit.index].id,
                        recordNum = recordStack.recordNum,
                        date = dateTimeState.dateLong,
                        type = if (type == RecordType.Expense) '-' else '+',
                        amount = if (unit.quantity.isNotBlank()) {
                            "%.2f".format(
                                Locale.US,
                                unit.amount.toDouble() * unit.quantity.toInt()
                            ).toDouble()
                        } else {
                            unit.amount.toDouble()
                        },
                        quantity = unit.quantity.ifBlank { null }?.toInt(),
                        categoryId = unit.categoryWithSubcategory.category.id,
                        subcategoryId = unit.categoryWithSubcategory.subcategory?.id,
                        accountId = account.id,
                        note = unit.note.ifBlank { null }
                    )
                )
            }
        }

        return recordList
    }

}