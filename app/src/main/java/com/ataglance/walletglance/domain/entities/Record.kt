package com.ataglance.walletglance.domain.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ataglance.walletglance.data.accounts.Account
import com.ataglance.walletglance.data.categories.CategoriesLists
import com.ataglance.walletglance.data.records.RecordStack
import com.ataglance.walletglance.data.records.RecordStackUnit
import com.ataglance.walletglance.data.records.RecordType
import com.ataglance.walletglance.ui.utils.asChar
import com.ataglance.walletglance.ui.utils.findById
import com.ataglance.walletglance.ui.utils.getRecordTypeByChar
import com.ataglance.walletglance.ui.utils.isExpenseOrIncome
import com.ataglance.walletglance.ui.utils.toCategoryType

@Entity(tableName = "Record")
data class Record(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val recordNum: Int,
    val date: Long,
    val type: Char,
    val accountId: Int,
    val amount: Double,
    val quantity: Int?,
    val categoryId: Int,
    val subcategoryId: Int?,
    val note: String?
) {

    fun isOutTransfer(): Boolean { return type == RecordType.OutTransfer.asChar() }

    fun toRecordStack(accountList: List<Account>, categoriesLists: CategoriesLists): RecordStack? {
        val recordType = getRecordTypeByChar(type) ?: return null
        val recordAccount = accountList.findById(accountId)?.toRecordAccount() ?: return null
        val recordStackUnit = toRecordStackUnit(categoriesLists) ?: return null

        return RecordStack(
            recordNum = recordNum,
            date = date,
            type = recordType,
            account = recordAccount,
            totalAmount = amount,
            stack = listOf(recordStackUnit)
        )
    }

    fun toRecordStackUnit(categoriesLists: CategoriesLists): RecordStackUnit? {
        val recordType = getRecordTypeByChar(type) ?: return null

        val parAndSubcategory = if (recordType.isExpenseOrIncome()) {
            categoriesLists.getParAndSubCategoryByIds(
                parentCategoryId = categoryId,
                subcategoryId = subcategoryId,
                type = recordType.toCategoryType()
            ) ?: return null
        } else {
            null to null
        }

        return RecordStackUnit(
            id = id,
            amount = amount,
            quantity = quantity,
            category = parAndSubcategory.first,
            subcategory = parAndSubcategory.second,
            note = note
        )
    }
}
