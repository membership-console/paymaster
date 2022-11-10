package cc.rits.membership.console.paymaster.util

import cc.rits.membership.console.paymaster.exception.ErrorCode
import cc.rits.membership.console.paymaster.exception.InternalServerErrorException
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.security.oauth2.client.clientcredentials.ClientCredentialsClient
import jakarta.inject.Singleton
import reactor.core.publisher.Mono

/**
 * 認証・認可のユーティリティ
 */
@Singleton
class AuthUtil(
    private val clientCredentialsClient: ClientCredentialsClient
) {

    private lateinit var accessToken: String

    /**
     * アクセストークンを取得
     *
     * @return アクセストークン
     */
    fun getAccessToken(): String {
        try {
            Mono.from(clientCredentialsClient.requestToken()) //
                .map {
                    accessToken = it.accessToken
                }.block()
            return accessToken
        } catch (exception: HttpClientResponseException) {
            throw InternalServerErrorException(ErrorCode.UNEXPECTED_ERROR)
        }
    }

}
