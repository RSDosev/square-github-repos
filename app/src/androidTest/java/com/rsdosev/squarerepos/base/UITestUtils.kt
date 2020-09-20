package com.rsdosev.squarerepos.base

import android.app.Activity
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers
import org.hamcrest.CoreMatchers

fun assertToastDisplayed(activity: Activity, toastTest: String) {
    Espresso.onView(ViewMatchers.withText(toastTest)).inRoot(
        RootMatchers.withDecorView(
            CoreMatchers.not(
                activity.window.decorView
            )
        )
    ).check(
        ViewAssertions.matches(
            ViewMatchers.isDisplayed()
        )
    )
}