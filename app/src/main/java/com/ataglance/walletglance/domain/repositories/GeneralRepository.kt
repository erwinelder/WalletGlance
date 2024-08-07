package com.ataglance.walletglance.domain.repositories

import androidx.room.Transaction

class GeneralRepository(
    private val settingsRepository: SettingsRepository,
    private val accountRepository: AccountRepository,
    private val categoryRepository: CategoryRepository,
    private val categoryCollectionRepository: CategoryCollectionRepository
) {
    @Transaction
    suspend fun resetAllData() {
        settingsRepository.saveIsSetUpPreference(0)
        accountRepository.deleteAllAccounts()
        categoryRepository.deleteAllCategories()
        categoryCollectionRepository.deleteAllCollections()
    }
}