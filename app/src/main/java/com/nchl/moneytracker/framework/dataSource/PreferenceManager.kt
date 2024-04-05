package com.nchl.moneytracker.framework.dataSource

import android.annotation.SuppressLint
import android.content.Context
import com.nchl.moneytracker.data.dataSource.pref.SharedPreferenceSource
import global.citytech.easydroid.core.preference.SecurePreference


@SuppressLint("StaticFieldLeak")
object PreferenceManager : SharedPreferenceSource {

    private lateinit var pref: SecurePreference

    private const val PREF_NAME = "money.tracker"
    private const val IS_APP_RUN_FIRST_TIME = "is_app_run_first_time"

    private var context: Context? = null

    fun init(context: Context) {
        this.context = context
        pref = SecurePreference(
            context,
            PREF_NAME
        )
    }

    override fun clearAllPreferences() {
        pref.clear()
    }

    override fun isApplicationOpenFirstTime(value: Boolean) = pref.retrieveBoolean(IS_APP_RUN_FIRST_TIME, false)

    override fun saveApplicationOpenFirstTime(value: Boolean) = pref.saveBoolean(IS_APP_RUN_FIRST_TIME, value)

}