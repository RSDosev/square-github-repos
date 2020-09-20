package com.rsdosev.domain.model

/**
 * Base class used for representing different result types from UseCases
 */
sealed class Result<out T> {

    data class Success<out T>(val data: T) : Result<T>()

    sealed class Error<out T> : Result<T>() {

        data class BasicError(
            val errorMsgCode: String? = null,
            val errorMsg: String?,
            val throwable: Throwable? = null
        ) : Error<Nothing>()

        object NetworkError : Error<Nothing>()

        data class ApiCallError(val code: Int? = null, val errorMsg: String? = null) :
            Error<Nothing>()
    }
}