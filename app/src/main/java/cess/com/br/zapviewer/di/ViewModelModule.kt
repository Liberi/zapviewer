package cess.com.br.zapviewer.di

import cess.com.br.zapviewer.productlist.ui.ProductListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { ProductListViewModel(get()) }
}