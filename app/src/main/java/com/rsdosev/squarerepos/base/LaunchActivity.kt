package com.rsdosev.squarerepos.base

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rsdosev.squarerepos.R

/**
 * The main and only one activity, responsible for holding the whole fragments based navigation
 */
class LaunchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)
    }
}