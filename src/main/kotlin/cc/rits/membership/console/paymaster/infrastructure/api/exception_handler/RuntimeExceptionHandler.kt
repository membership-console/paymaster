package cc.rits.membership.console.paymaster.infrastructure.api.exception_handler

import cc.rits.membership.console.paymaster.exception.ErrorCode
import cc.rits.membership.console.paymaster.infrastructure.api.response.ErrorResponse
import io.micronaut.context.annotation.Requires
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Produces
import io.micronaut.http.server.exceptions.ExceptionHandler
import jakarta.inject.Singleton
import org.slf4j.LoggerFactory

/**
 * RuntimeExceptionハンドラー
 */
@Produces
@Singleton
@Requires(classes = [RuntimeException::class, ExceptionHandler::class])
class RuntimeExceptionHandler : ExceptionHandler<RuntimeException, HttpResponse<ErrorResponse>> {
    /**
     * RuntimeExceptionが発生した場合は500エラー
     */
    override fun handle(request: HttpRequest<*>, exception: RuntimeException): HttpResponse<ErrorResponse> {
        LoggerFactory.getLogger(RuntimeExceptionHandler::class.java).error(exception.message)
        val errorCode = ErrorCode.UNEXPECTED_ERROR
        return HttpResponse.status<ErrorResponse?>(HttpStatus.INTERNAL_SERVER_ERROR) //
            .body(ErrorResponse(errorCode.code, errorCode.message))
    }
}
