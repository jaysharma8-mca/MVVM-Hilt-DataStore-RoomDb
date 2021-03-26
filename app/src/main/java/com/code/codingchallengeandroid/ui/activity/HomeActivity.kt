package com.code.codingchallengeandroid.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import com.code.codingchallengeandroid.R
import com.code.codingchallengeandroid.utils.startNewActivity
import dagger.hilt.android.AndroidEntryPoint

@Suppress("DEPRECATION")
@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    lateinit var handler: Handler
    private lateinit var runnable: Runnable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        handler = Handler()
        runnable = Runnable {
            Toast.makeText(
                this@HomeActivity,
                "user is inactive from last 30 seconds",
                Toast.LENGTH_SHORT
            ).show()
            startNewActivity(AuthActivity::class.java)

        }
        startHandler()
    }

    override fun onUserInteraction() {
        // TODO Auto-generated method stub
        super.onUserInteraction()
        stopHandler()
        startHandler()
    }

    private fun stopHandler() {
        handler.removeCallbacks(runnable)
    }

    private fun startHandler() {
        handler.postDelayed(runnable, 30000.toLong())
    }
}