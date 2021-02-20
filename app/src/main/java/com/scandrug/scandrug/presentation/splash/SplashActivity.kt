package com.scandrug.scandrug.presentation.splash

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.scandrug.scandrug.BuildConfig
import com.scandrug.scandrug.R
import com.scandrug.scandrug.base.BaseApplication
import com.scandrug.scandrug.data.local.AppPreferences
import com.scandrug.scandrug.presentation.authentication.AuthenticationActivity
import com.scandrug.scandrug.presentation.home.MainActivity
import com.scandrug.scandrug.presentation.home.delivery.DeliveryActivity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

class SplashActivity : AppCompatActivity() {
    private var SPLASH_DISPLAY_LENGTH = 4000
    private lateinit var intentNavigation: Intent
    private var sharedPreferences =
        BaseApplication.instance.getSharedPreferences(BuildConfig.PREF_NAME, MODE_PRIVATE)
    private var appPreferences: AppPreferences = AppPreferences(sharedPreferences)
    private var token: String = appPreferences.getAccessToken().toString()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        changeStatusBarColor(window)
        Observable.interval(1, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext { x: Long? -> }
            .takeUntil { aLong: Long -> aLong == SPLASH_DISPLAY_LENGTH.toLong() }
            .doOnComplete {
                when {
                    token.isEmpty() -> Intent(this, AuthenticationActivity::class.java).also { intentNavigation=it }
                    token == "company" -> Intent(this, MainActivity::class.java).also { intentNavigation = it }
                    token == "delivery" -> Intent(this, DeliveryActivity::class.java).also { intentNavigation = it }
                }
                startActivity(intentNavigation)
                finish()
            }.subscribe()
    }


    private fun changeStatusBarColor(window: Window) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            ContextCompat.getColor(this@SplashActivity, R.color.colorPrimary).also {
                window.statusBarColor = it
            }
        }
    }
}