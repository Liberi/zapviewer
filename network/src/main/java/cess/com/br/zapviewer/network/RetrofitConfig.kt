package cess.com.br.zapviewer.network

import java.util.concurrent.TimeUnit
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory.createWithScheduler
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitConfig {
    private const val TIMEOUT = 30L

    fun createApi(baseUrl: String): BaseApi {
        val okFullHttpClient = OkHttpClient.Builder()
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .build()

        return buildApi(
            baseUrl,
            okFullHttpClient
        ).create(BaseApi::class.java)
    }

    private fun buildApi(url: String, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(url)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(createWithScheduler(Schedulers.io()))
            .build()
    }

}

