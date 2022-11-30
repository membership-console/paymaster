package cc.rits.membership.console.paymaster.infrastructure.api.exception_handler

import cc.rits.membership.console.paymaster.exception.ErrorCode
import cc.rits.membership.console.paymaster.infrastructure.api.response.ErrorResponse
import io.micronaut.context.annotation.Replaces
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.MutableHttpResponse
import io.micronaut.security.authentication.AuthorizationException
import io.micronaut.security.authentication.DefaultAuthorizationExceptionHandler
import jakarta.inject.Singleton

@Singleton
@Replaces(DefaultAuthorizationExceptionHandler::class)
class AuthorizationExceptionHandler : DefaultAuthorizationExceptionHandler() {

    override fun handle(request: HttpRequest<*>?, exception: AuthorizationException?): MutableHttpResponse<*> {
        if (exception != null && exception.isForbidden) {
            val errorCode = ErrorCode.USER_HAS_NO_PERMISSION
            return super.handle(request, exception) //
                .body(ErrorResponse(errorCode.code, errorCode.message)) //
                .status(HttpStatus.FORBIDDEN)
        }
        val errorCode = ErrorCode.USER_NOT_LOGGED_IN
        return super.handle(request, exception) //
            .body(ErrorResponse(errorCode.code, errorCode.message)) //
            .status(HttpStatus.UNAUTHORIZED)
    }

}
