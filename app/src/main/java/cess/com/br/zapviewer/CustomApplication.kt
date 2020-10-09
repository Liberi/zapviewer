package cess.com.br.zapviewer

import android.app.Application
import cess.com.br.zapviewer.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

open class CustomApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        setupKoin()
    }

    protected open fun setupKoin() {
        startKoin {
            modules(
                listOf(
                    networkModule
                )
            )
            androidContext(this@CustomApplication)
        }
    }
}