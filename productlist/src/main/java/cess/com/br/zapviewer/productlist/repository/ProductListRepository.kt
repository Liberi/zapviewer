package cess.com.br.zapviewer.productlist.repository

import cess.com.br.zapviewer.common.model.Product
import cess.com.br.zapviewer.productlist.mapper.ProductListMapper
import cess.com.br.zapviewer.network.BaseApi
import io.reactivex.Single

open class ProductListRepository(private val api: BaseApi) {
    fun getProductList(productFlavor: String): Single<List<Product>> = api.fetchProducts().map { ProductListMapper.map(it, productFlavor) }
}