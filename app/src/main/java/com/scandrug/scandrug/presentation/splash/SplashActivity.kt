package com.scandrug.scandrug.presentation.splash
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.scandrug.scandrug.R
import com.scandrug.scandrug.presentation.home.MainActivity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

class SplashActivity : AppCompatActivity() {
    private var SPLASH_DISPLAY_LENGTH = 4000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        changeStatusBarColor(window)
        Observable.interval(1, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext { x: Long? -> }
            .takeUntil { aLong: Long -> aLong == SPLASH_DISPLAY_LENGTH.toLong() }
            .doOnComplete {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }.subscribe()
    }


    fun changeStatusBarColor(window: Window) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = resources.getColor(R.color.colorPrimary)
        }
    }
}