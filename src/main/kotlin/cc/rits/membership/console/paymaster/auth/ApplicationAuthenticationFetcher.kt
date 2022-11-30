package cc.rits.membership.console.paymaster.auth

import cc.rits.membership.console.paymaster.client.response.UserInfoResponse
import cc.rits.membership.console.paymaster.enums.UserRole
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
                val authorization = request.headers["Authorization"]
                if (authorization != null) {
                    try {
                        val userInfoResponse = ObjectMapper().readValue(authorization.substring(5), UserInfoResponse::class.java)
                        val roles = userInfoResponse.userGroups.flatMap { userGroup ->
                            userGroup.roles.mapNotNull {
                                UserRole.findById(it)
                            }.map {
                                it.toString()
                            }
                        }
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
