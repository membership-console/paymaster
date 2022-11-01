package cc.rits.membership.console.paymaster.infrastructure.api.exception_handler

import cc.rits.membership.console.paymaster.exception.BaseException
import cc.rits.membership.console.paymaster.infrastructure.api.response.ErrorResponse
import io.micronaut.context.annotation.Requires
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Produces
import io.micronaut.http.server.exceptions.ExceptionHandler
import jakarta.inject.Singleton
import org.slf4j.LoggerFactory

/**
 * BaseExceptionハンドラー
 */
@Produces
@Singleton
@Requires(classes = [BaseException::class, ExceptionHandler::class])
class BaseExceptionHandler : ExceptionHandler<BaseException, HttpResponse<ErrorResponse>> {
    /**
     * BaseExceptionのハンドラー
     */
    override fun handle(request: HttpRequest<*>, exception: BaseException): HttpResponse<ErrorResponse> {
        LoggerFactory.getLogger(BaseExceptionHandler::class.java).error(exception.errorCode.message)
        val errorCode = exception.errorCode
        return HttpResponse.status<ErrorResponse?>(exception.status) //
            .body(ErrorResponse(errorCode.code, errorCode.message))
    }

}
