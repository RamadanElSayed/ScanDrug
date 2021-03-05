package com.scandrug.scandrug.base
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatDelegate
import com.scandrug.scandrug.BuildConfig
import com.scandrug.scandrug.data.local.AppPreferences
import com.scandrug.scandrug.utils.AppThemeMode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BaseApplication : Application() {
    private lateinit var appPreferences: AppPreferences
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate() {
        super.onCreate()
        //pref

        sharedPreferences = this.getSharedPreferences(BuildConfig.PREF_NAME, Context.MODE_PRIVATE)
        appPreferences = AppPreferences(sharedPreferences)
        instance = this
        //get the theme mode from pref
        getThemMode()

        //setRoamingData()
    }

//    private fun setRoamingData() {
//        val bnkitDatabase: BnkitDatabase = Room.databaseBuilder(
//            instance,
//            BnkitDatabase::class.java,
//            DB_NAME
//        ).build()
//       bnkitDao = bnkitDatabase.bnkitDao()
//    }


    private fun getThemMode() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                var mode: Int
                if (appPreferences.getThemeMode() == 0) {
                    if (isPreAndroidTen()) {
                        mode = AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY
                    } else {
                        mode = AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
                    }
                } else {
                    mode = when (appPreferences.getThemeMode()) {
                        AppThemeMode.LIGHT.ordinal -> AppCompatDelegate.MODE_NIGHT_NO
                        AppThemeMode.DARK.ordinal -> AppCompatDelegate.MODE_NIGHT_YES
                        AppThemeMode.SYSTEM.ordinal -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
                        AppThemeMode.BATTERY.ordinal -> AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY
                        else -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
                    }
                }
                AppCompatDelegate.setDefaultNightMode(mode)
            } catch (e: Exception) {

            }

        }
    }

    private fun isPreAndroidTen() = Build.VERSION.SDK_INT < Build.VERSION_CODES.Q

    companion object {
        lateinit var instance: BaseApplication
//        private var bnkitDao: BnkitDao? = null
//        fun getBnkitDao(): BnkitDao? {
//            return bnkitDao
//        }
    }

}