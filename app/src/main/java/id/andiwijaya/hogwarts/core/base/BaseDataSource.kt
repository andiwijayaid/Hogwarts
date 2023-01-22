package id.andiwijaya.hogwarts.core.base

import id.andiwijaya.hogwarts.core.Result
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

abstract class BaseDataSource {

    suspend fun <T> getResultWithSingleObject(call: suspend () -> Response<T>): Result<T> = try {
        val response = call()

        if (response.isSuccessful) response.body()?.let {
            Result.Success(it)
        } ?: handlerErrorBody(response)
        else handlerErrorBody(response)
    } catch (e: HttpException) {
        Result.Error(e.localizedMessage ?: "An unexpected error occurred")
    } catch (e: IOException) {
        Result.Error("Server not found. Please check your internet connection")
    }

    private fun <T> handlerErrorBody(response: Response<T>): Result<T> {
        return Result.Error(
            "An unexpected error occurred",
            null,
            response.code()
        )
    }

}