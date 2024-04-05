package com.nchl.moneytracker.domain.usecase

import com.nchl.moneytracker.domain.model.Category
import com.nchl.moneytracker.domain.model.DatabaseReponse
import com.nchl.moneytracker.domain.model.User
import com.nchl.moneytracker.domain.repository.LocalRepository
import com.nchl.moneytracker.presentation.model.ExpenseCategory
import io.reactivex.Observable


class LocalDataUseCase(private val localRepository: LocalRepository) {
     fun getUsers(): Observable<List<User>> = localRepository.getUsers()
     fun getCategories(): Observable<List<Category>> = localRepository.getCategories()
     fun saveCategories(categories:List<ExpenseCategory>): Observable<DatabaseReponse>  = localRepository.saveCategories(categories)
}