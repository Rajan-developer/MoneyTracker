package com.nchl.moneytracker.data.dataSource.pref

interface SharedPreferenceSource {
    fun clearAllPreferences()
    fun isApplicationOpenFirstTime(value:Boolean):Boolean
    fun saveApplicationOpenFirstTime(value:Boolean)
}