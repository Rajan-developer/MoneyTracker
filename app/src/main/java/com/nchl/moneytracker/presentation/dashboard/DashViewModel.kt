package com.nchl.moneytracker.presentation.dashboard

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import com.nchl.moneytracker.R
import com.nchl.moneytracker.data.repository.LocalRepositoryImpl
import com.nchl.moneytracker.domain.usecase.LocalDataUseCase
import com.nchl.moneytracker.framework.dataSource.LocalDatabaseSourceImpl
import com.nchl.moneytracker.framework.dataSource.PreferenceManager
import com.nchl.moneytracker.presentation.login.LoginViewModel
import com.nchl.moneytracker.presentation.model.ExpenseCategory
import com.nchl.moneytracker.presentation.utils.AppUtility.drawableToByteArray
import com.nchl.moneytracker.presentation.utils.log.Logger
import global.citytech.easydroid.core.mvvm.BaseAndroidViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class DashViewModel(private val context: Application) : BaseAndroidViewModel(context) {

    private val TAG = Logger(LoginViewModel::class.java.name).toString()
    val firstTimeAppOpen = MutableLiveData<Boolean>()

    var localDataUseCase: LocalDataUseCase = LocalDataUseCase(
        LocalRepositoryImpl(
            LocalDatabaseSourceImpl(),
            PreferenceManager
        )
    )

    fun isApplicationOpenFirstTime() {
        if (PreferenceManager.isApplicationOpenFirstTime(true)) {
            firstTimeAppOpen.value = true
            PreferenceManager.saveApplicationOpenFirstTime(false)
        }else{
            this.compositeDisposable.add(
                this.localDataUseCase.getCategories()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        {
                            Logger.getLogger(TAG)
                                .debug("Category inserting ${it.size}................")
                        },
                        {
                            Logger.getLogger(TAG).debug("Category insert ${it.message}................")
                        }
                    )
            )
        }
    }

    fun saveInitialCategoryList() {
        var categories: List<ExpenseCategory> = staticCategories()
        this.compositeDisposable.add(
            this.localDataUseCase.saveCategories(categories)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        Logger.getLogger(TAG)
                            .debug("Category inserting ${it.message}................")
                    },
                    {
                        Logger.getLogger(TAG).debug("Category insert ${it.message}................")
                    }
                )
        )
    }

    fun staticCategories(): List<ExpenseCategory> {
        var categories: ArrayList<ExpenseCategory> = arrayListOf()

        categories.add(
            ExpenseCategory(
                "Education",
                "0",
                drawableToByteArray(context, context.getDrawable(R.drawable.ic_cat_food)!!)
            )
        )

        categories.add(
            ExpenseCategory(
                "Clothing",
                "0",
                drawableToByteArray(context, context.getDrawable(R.drawable.ic_cat_food)!!)
            )
        )

        categories.add(
            ExpenseCategory(
                "Vehicle",
                "0",
                 drawableToByteArray(context, context.getDrawable(R.drawable.ic_cat_food)!!)
            )
        )

        categories.add(
            ExpenseCategory(
                "Health",
                "0",
                 drawableToByteArray(context, context.getDrawable(R.drawable.ic_cat_food)!!)
            )
        )

        categories.add(
            ExpenseCategory(
                "Shopping",
                "0",
                 drawableToByteArray(context, context.getDrawable(R.drawable.ic_cat_food)!!)
            )
        )

        categories.add(
            ExpenseCategory(
                "Salary",
                "1",
                 drawableToByteArray(context, context.getDrawable(R.drawable.ic_cat_food)!!)
            )
        )

        categories.add(
            ExpenseCategory(
                "Lottery",
                "1",
                 drawableToByteArray(context, context.getDrawable(R.drawable.ic_cat_food)!!)
            )
        )

        categories.add(
            ExpenseCategory(
                "Rental",
                "1",
                 drawableToByteArray(context, context.getDrawable(R.drawable.ic_cat_food)!!)
            )
        )

        categories.add(
            ExpenseCategory(
                "Sale",
                "1",
                 drawableToByteArray(context, context.getDrawable(R.drawable.ic_cat_food)!!)
            )
        )

        categories.add(
            ExpenseCategory(
                "Refund",
                "1",
                 drawableToByteArray(context, context.getDrawable(R.drawable.ic_cat_food)!!)
            )
        )
        return categories
    }
}