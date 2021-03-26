package com.code.codingchallengeandroid

import android.app.Application
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import com.code.codingchallengeandroid.ui.activity.AuthActivity
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApplication : Application(), LifecycleObserver{
    override fun onCreate() {
        super.onCreate()
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onAppBackgrounded() {
        Handler(Looper.getMainLooper()).postDelayed({
            Log.d("AppBackground", "App in background")
            Intent(this, AuthActivity::class.java).also {
                it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(it)
            }
        }, 10000)


    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onAppForegrounded() {
        Log.d("AppForeground", "App in foreground")
    }
}