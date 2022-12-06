package cc.rits.membership.console.paymaster.auth

import cc.rits.membership.console.paymaster.client.response.UserInfoResponse
import cc.rits.membership.console.paymaster.exception.ErrorCode
import cc.rits.membership.console.paymaster.exception.UnauthorizedException
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import io.micronaut.http.HttpRequest
import io.micronaut.security.authentication.Authentication
import io.micronaut.security.filters.AuthenticationFetcher
import jakarta.inject.Singleton
import org.reactivestreams.Publisher
import reactor.core.publisher.Mono
import java.util.Base64

/**
 * AuthenticationFetcher
 */
@Singleton
class ApplicationAuthenticationFetcher : AuthenticationFetcher {

    /**
     * 認証情報を取得
     *
     * @param request HTTP request
     *
     * @return 認証情報
     */
    override fun fetchAuthentication(request: HttpRequest<*>?): Publisher<Authentication> {
        return Mono.create { emitter ->
            if (request != null) {
                val authorization = request.headers["X-Membership-Console-User"]
                if (authorization != null) {
                    try {
                        val authorizationInfo = Base64.getDecoder().decode(authorization)
                        val userInfoResponse = ObjectMapper().readValue(authorizationInfo, UserInfoResponse::class.java)
                        val roles = userInfoResponse.roles
                        emitter.success(Authentication.build(userInfoResponse.id.toString(), roles))
                    } catch (exception: JsonProcessingException) {
                        emitter.error(UnauthorizedException(ErrorCode.USER_NOT_LOGGED_IN))
                    }
                }
            }
            emitter.success()
        }
    }

}
