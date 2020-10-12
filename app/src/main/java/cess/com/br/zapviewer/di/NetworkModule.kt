package cess.com.br.zapviewer.di

import cess.com.br.zapviewer.network.BuildConfig.BASE_URL
import cess.com.br.zapviewer.network.RetrofitConfig
import org.koin.dsl.module

val networkModule = module {
   single { RetrofitConfig.createApi(BASE_URL) }
}