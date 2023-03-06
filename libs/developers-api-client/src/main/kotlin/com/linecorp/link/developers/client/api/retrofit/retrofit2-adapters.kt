/*
 *   Copyright 2023 LINE Corporation
 *
 *   LINE Corporation licenses this file to you under the Apache License,
 *   version 2.0 (the "License"); you may not use this file except in compliance
 *   with the License. You may obtain a copy of the License at:
 *
 *     https:www.apache.orglicensesLICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 *   WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 *   License for the specific language governing permissions and limitations
 *   under the License.
 *
 */

package com.linecorp.link.developers.client.api.retrofit

import com.linecorp.link.developers.client.response.GenericResponse
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import okhttp3.Request
import okhttp3.ResponseBody
import okio.Timeout
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Converter
import retrofit2.Response
import retrofit2.Retrofit

@Suppress("TooManyFunctions")
internal class NetworkResponseCall<R : Any>(
    private val delegate: Call<R>,
    private val errorBodyConverter: Converter<ResponseBody, R>
) : Call<R> {
    override fun clone(): Call<R> {
        return NetworkResponseCall(delegate.clone(), errorBodyConverter)
    }

    override fun execute(): Response<R> {
        throw UnsupportedOperationException("NetworkResponseCall doesn't support execute")
    }

    override fun enqueue(callback: Callback<R>) {
        return this.delegate.enqueue(object : Callback<R> {
            override fun onResponse(call: Call<R>, response: Response<R>) {
                val body = response.body()
                val error = response.errorBody()

                if (response.isSuccessful) {
                    body?.let {
                        callback.onResponse(this@NetworkResponseCall, successBody(body))
                    } ?: callback.onResponse(this@NetworkResponseCall, unknownError(body))
                } else {
                    val errorBody = resolveErrorBody(error)
                    errorBody?.let {
                        callback.onResponse(this@NetworkResponseCall, apiError(errorBody))
                    } ?: callback.onResponse(this@NetworkResponseCall, unknownError(errorBody))
                }
            }

            override fun onFailure(call: Call<R>, throwable: Throwable) {
                val response: Response<R> = resoleFailure(throwable)
                callback.onResponse(this@NetworkResponseCall, response)
            }
        })
    }

    private fun resoleFailure(throwable: Throwable): Response<R> {
        val response = GenericResponse.unknownResponse(throwable)
        @Suppress("UNCHECKED_CAST")
        return Response.success(response as R)
    }

    @Suppress("TooGenericExceptionCaught", "SwallowedException")
    private fun resolveErrorBody(error: ResponseBody?): R? {
        val errorBody = when {
            error == null || error.contentLength() == 0L -> null
            else -> try {
                errorBodyConverter.convert(error)
            } catch (e: Exception) {
                null
            }
        }
        return errorBody
    }

    private fun apiError(errorBody: R): Response<R> {
        return Response.success(errorBody)
    }


    private fun successBody(body: R): Response<R> {
        return Response.success(body)
    }


    private fun unknownError(body: R?): Response<R> {
        return Response.success(body)
    }

    override fun isExecuted(): Boolean = delegate.isExecuted

    override fun cancel() = delegate.cancel()

    override fun isCanceled(): Boolean = delegate.isCanceled

    override fun request(): Request = delegate.request()

    override fun timeout(): Timeout = delegate.timeout()
}


class NetworkResponseAdapter<R : Any>(
    private val successType: Type,
    private val errorBodyConverter: Converter<ResponseBody, R>
) : CallAdapter<R, Call<R>> {
    override fun responseType(): Type = successType

    override fun adapt(call: Call<R>): Call<R> {
        return NetworkResponseCall(call, errorBodyConverter)
    }

}


class NetworkResponseAdapterFactory : CallAdapter.Factory() {
    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        return if (Call::class.java != getRawType(returnType)) {
            null
        } else {
            check(returnType is ParameterizedType) {
                "return type must be parameterized as Call<NetworkResponse<<Foo>> or Call<NetworkResponse<out Foo>>"
            }

            val responseType = getParameterUpperBound(0, returnType)
            if (getRawType(responseType) != GenericResponse::class.java) {
                null
            } else {
                // the response type is Service and should be parameterized
                check(responseType is ParameterizedType)
                { "Response must be parameterized as NetworkResponse<Foo> or NetworkResponse<out Foo>" }

                val errorBodyConverter = retrofit.nextResponseBodyConverter<Any>(
                    null,
                    responseType,
                    annotations
                )
                NetworkResponseAdapter<Any>(responseType, errorBodyConverter)
            }
        }
    }
}
