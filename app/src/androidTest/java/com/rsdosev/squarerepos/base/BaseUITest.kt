package com.rsdosev.squarerepos.base

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.rsdosev.domain.CoroutineContextProvider
import com.rsdosev.domain.usecase.GetSquareReposUseCase
import com.rsdosev.squarerepos.mock.MockGetSquareReposUseCase
import com.rsdosev.squarerepos.di.applicationModule
import com.rsdosev.squarerepos.di.dataModule
import com.rsdosev.squarerepos.di.presentationModule
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.dsl.module

@RunWith(AndroidJUnit4::class)
open class BaseUITest {

    /**
     * list of all the Koin modules needed + mocked Domain module
     */
    private val diModules = listOf(
        applicationModule,
        dataModule,
        // Mocked domain module
        module {
            single { CoroutineContextProvider() }

            factory<GetSquareReposUseCase> {
                MockGetSquareReposUseCase()
            }
        },
        presentationModule
    )

    @Before
    fun setUp() {
        loadKoinModules(diModules)
    }

    @After
    fun tearDown() {
        unloadKoinModules(diModules)
    }
}