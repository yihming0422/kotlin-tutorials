package com.bennyhuo.kotlin.advancedtypes.lateinitproperties

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bennyhuo.kotlin.advancedtypes.R

class UserActivity: AppCompatActivity() {

    private val nameView by lazy {
        findViewById<TextView>(R.id.nameView)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
        nameView.text = "Benny"
    }

}