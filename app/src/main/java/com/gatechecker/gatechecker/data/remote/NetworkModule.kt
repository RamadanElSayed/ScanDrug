package com.bnkit.bnkit.data.remote
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.security.KeyManagementException
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import javax.net.ssl.*
object NetworkModule {

    fun bindApiService(baseUrl: String): NetworkService {
        val retrofit=provideRetrofit(baseUrl)
        return retrofit.create(NetworkService::class.java)
    }

    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor? {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    @Singleton
    fun provideSSLSocketFactory(): SSLSocketFactory? {
        var sslContext: SSLContext? = null
        try {
            sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, provideTrustManager(), SecureRandom())
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        } catch (e: KeyManagementException) {
            e.printStackTrace()
        }
        return sslContext!!.socketFactory
    }

    @Singleton
    fun provideOkHttpClientBuilder(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.sslSocketFactory(
            provideSSLSocketFactory()!!,
            (provideTrustManager()[0] as X509TrustManager?)!!
        )
        builder.hostnameVerifier(HostnameVerifier { hostName: String?, session: SSLSession? -> true })
        builder.connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .writeTimeout(120, TimeUnit.SECONDS)
        builder.addInterceptor { chain ->
            val newRequest = chain.request().newBuilder()
                .addHeader("Content_Type", "application/json").build()
//            //if(CacheInMemory.loadToken()!=null)
//         .addHeader("Authorization", "Bearer " + CacheInMemory.loadToken())
//                .build()
            chain.proceed(newRequest)
        }
        builder.addInterceptor(provideHttpLoggingInterceptor()!!)
        return builder.build()
    }

    private fun provideRetrofit(baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(provideOkHttpClientBuilder())
            .build()
    }


    private fun provideTrustManager(): Array<TrustManager?> {
        return arrayOf(
            object : X509TrustManager {
                @Suppress("TrustAllX509TrustManager")
                @Throws(CertificateException::class)
                override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {
                }

                @Suppress("TrustAllX509TrustManager")
                @Throws(CertificateException::class)
                override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {
                }

                override fun getAcceptedIssuers(): Array<X509Certificate> {
                    return arrayOf()
                }
            }
        )
    }

}