package com.nchl.moneytracker.domain.usecase

import com.nchl.moneytracker.domain.model.*
import com.nchl.moneytracker.domain.repository.LocalRepository
import com.nchl.moneytracker.presentation.model.CategorySum
import com.nchl.moneytracker.presentation.model.ExpenseCategory
import com.nchl.moneytracker.presentation.model.ExpenseTransaction
import io.reactivex.Observable


class LocalDataUseCase(private val localRepository: LocalRepository) {
     fun getUsers(): Observable<List<User>> = localRepository.getUsers()
     fun getCategories(): Observable<List<Category>> = localRepository.getCategories()
     fun getIncomeCategories(categoryType: String): Observable<List<Category>> = localRepository.getIncomeCategories(categoryType)
     fun getExpenseCategories(categoryType: String): Observable<List<Category>> = localRepository.getExpenseCategories(categoryType)
     fun getCategoriesByType(categoryType: String): Observable<List<Category>> = localRepository.getCategoriesByType(categoryType)
     fun deleteCategoryById(categoryId: String): Observable<Boolean> = localRepository.deleteCategoryById(categoryId)
     fun saveCategories(categories:List<ExpenseCategory>): Observable<DatabaseReponse>  = localRepository.saveCategories(categories)
     fun updateCategory(category:ExpenseCategory): Observable<Boolean>  = localRepository.updateCategory(category)
     fun addCategory(category:ExpenseCategory): Observable<Boolean>  = localRepository.addCategory(category)
     fun addTransaction(transaction: ExpenseTransaction): Observable<Boolean>  = localRepository.addTransaction(transaction)
     fun updateTransactionById(transaction: ExpenseTransaction): Observable<Boolean>  = localRepository.updateTransactionById(transaction)
     fun deleteTransactionByID(transaction: ExpenseTransaction): Observable<Boolean>  = localRepository.deleteTransactionByID(transaction)
     fun getTransactionByDate(transactionDate: String): Observable<List<Transaction>>  = localRepository.getTransactionByDate(transactionDate)
     fun getTotalIncomeByDate(transactionDate: String, categoryType: String): Observable<Double>  = localRepository.getTotalIncomeByDate(transactionDate,categoryType)
     fun getTransactionSumByCategory(transactionDate: String, categoryType: String): Observable<List<CategorySum>>  = localRepository.getTransactionSumByCategory(transactionDate,categoryType)
}