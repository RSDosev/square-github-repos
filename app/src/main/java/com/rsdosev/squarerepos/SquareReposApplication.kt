package com.rsdosev.squarerepos

import android.app.Application
import com.rsdosev.squarerepos.di.applicationModule
import com.rsdosev.squarerepos.di.dataModule
import com.rsdosev.squarerepos.di.domainModule
import com.rsdosev.squarerepos.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class SquareReposApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initDependencyInjectionFramework()
    }

    private fun initDependencyInjectionFramework() {
        startKoin {
            androidLogger()
            androidContext(this@SquareReposApplication)
            modules(
                listOf(
                    applicationModule,
                    dataModule,
                    domainModule,
                    presentationModule
                )
            )
        }
    }
}