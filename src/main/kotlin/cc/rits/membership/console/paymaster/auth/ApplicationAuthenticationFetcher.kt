package cc.rits.membership.console.paymaster.auth

import cc.rits.membership.console.paymaster.client.IAMClient
import cc.rits.membership.console.paymaster.enums.UserRole
import cc.rits.membership.console.paymaster.exception.ErrorCode
import cc.rits.membership.console.paymaster.exception.InternalServerErrorException
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
class ApplicationAuthenticationFetcher(
    private val iamClient: IAMClient
) : AuthenticationFetcher {

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
                if (request.cookies["SESSION"] != null) {
                    val cookies = request.cookies.map {
                        it.value
                    }.toSet()
                    val response = iamClient.getUserInfo(cookies)
                    if (response != null) {
                        val roles = response.userGroups.flatMap { userGroup ->
                            userGroup.roles.mapNotNull {
                                UserRole.findById(it)
                            }.map {
                                it.toString()
                            }
                        }
                        emitter.success(Authentication.build(response.id.toString(), roles))
                    } else {
                        emitter.error(InternalServerErrorException(ErrorCode.UNEXPECTED_ERROR))
                    }
                }
            }
            emitter.success()
        }
    }

}
