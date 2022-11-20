package cc.rits.membership.console.paymaster.util

import cc.rits.membership.console.paymaster.BaseSpec
import cc.rits.membership.console.paymaster.exception.BaseException
import cc.rits.membership.console.paymaster.exception.ErrorCode
import cc.rits.membership.console.paymaster.exception.InternalServerErrorException
import io.micronaut.http.HttpResponse
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.security.oauth2.client.clientcredentials.ClientCredentialsClient
import io.micronaut.security.oauth2.endpoint.token.response.TokenResponse
import io.micronaut.test.annotation.MockBean
import jakarta.inject.Inject
import reactor.core.publisher.Mono
/**
 * AuthUtilの単体テスト
 */
class AuthUtil_UT extends BaseSpec {

    @Inject
    AuthUtil sut

    @Inject
    ClientCredentialsClient clientCredentialsClient

    @MockBean(ClientCredentialsClient)
    ClientCredentialsClient clientCredentialsClient() {
        return Mock(ClientCredentialsClient)
    }


    def "getAccessToken: アクセストークンを取得できる"() {
        given:
        final tokenResponse = new TokenResponse(
            "dummy",
            "bearer"
        )

        when:
        final result = this.sut.getAccessToken()

        then:
        1 * this.clientCredentialsClient.requestToken() >> Mono.just(tokenResponse)
        result == tokenResponse.accessToken
    }

    def "getAccessToken: トークンを取得できない場合は500エラー"() {
        setup:
        clientCredentialsClient.requestToken() >> {
            throw new HttpClientResponseException("", HttpResponse.notFound())
        }

        when:
        this.sut.getAccessToken()

        then:
        BaseException exception = thrown()
        verifyException(exception, new InternalServerErrorException(ErrorCode.UNEXPECTED_ERROR))
    }

}
