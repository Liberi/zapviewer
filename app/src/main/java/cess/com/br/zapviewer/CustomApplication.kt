package cess.com.br.zapviewer

import android.app.Application
import cess.com.br.zapviewer.di.networkModule
import cess.com.br.zapviewer.di.repositoryModule
import cess.com.br.zapviewer.di.viewModelModule
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
                    networkModule,
                    viewModelModule,
                    repositoryModule
                )
            )
            androidContext(this@CustomApplication)
        }
    }
}