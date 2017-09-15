package com.rafaltrzcinski.dribshots.rest.api

import com.rafaltrzcinski.dribshots.di.Injector
import okhttp3.Interceptor
import okhttp3.Response

class RequestInterceptor : Interceptor {

    companion object {
        val HEADER_AUTH = "Authorization"
        val HEADER_AUTH_PREFIX = "Bearer"
        val userController = Injector.component.getUserController()
    }

    override fun intercept(chain: Interceptor.Chain?): Response {
        val request = chain?.request()
                ?.newBuilder()
                ?.addHeader(HEADER_AUTH, "${HEADER_AUTH_PREFIX} ${userController.getAccessToken()}")
                ?.build()

        return chain!!.proceed(request)
    }
}