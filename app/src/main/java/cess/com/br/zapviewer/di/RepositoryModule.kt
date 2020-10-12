package cess.com.br.zapviewer.di

import cess.com.br.zapviewer.productlist.repository.ProductListRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { ProductListRepository(get()) }
}