package cess.com.br.zapviewer.network

import cess.com.br.zapviewer.network.model.ProductSourceResponse
import io.reactivex.Single
import retrofit2.http.*

interface BaseApi {
    @GET
    fun fetchProducts(): Single<ProductSourceResponse>
}