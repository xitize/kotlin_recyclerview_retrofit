package com.kshtitiz.sundaymobilityapp.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.kshtitiz.sundaymobilityapp.R

class SplashActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //hiding title bar of this activity
        //  window.requestFeature(Window.FEATURE_NO_TITLE)
        //making this activity full screen
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        //sets ascent color for navigation color
        window.navigationBarColor = resources.getColor(R.color.colorAccent)

        //setting contentView after changing windows
        setContentView(R.layout.activity_splash)

        //making splash 2 sec delay
        Handler().postDelayed({
            //starts MainActivity after 2 sec
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)   //disabling the animation while activity open
            startActivity(intent)
            overridePendingTransition(0, 0) //setting animation  to  zero to enter and exit
            finish()

        }, 2000)

    }


}
