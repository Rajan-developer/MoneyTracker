package com.nchl.moneytracker

import android.app.Application
import com.nchl.moneytracker.framework.dataSource.PreferenceManager

class MoneyTrackerApplication : Application() {

    companion object {
        lateinit var INSTANCE: MoneyTrackerApplication
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        PreferenceManager.init(this)
    }
}