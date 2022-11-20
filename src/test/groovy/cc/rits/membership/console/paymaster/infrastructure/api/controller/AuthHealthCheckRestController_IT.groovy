package cc.rits.membership.console.paymaster.infrastructure.api.controller

import cc.rits.membership.console.paymaster.auth.MockAuthenticationFetcher
import cc.rits.membership.console.paymaster.enums.UserRole
import io.micronaut.http.HttpStatus
import io.micronaut.security.authentication.Authentication
import jakarta.inject.Inject

class AuthHealthCheckRestController_IT extends BaseRestController_IT {
    // API PATH
    static final BASE_PATH = "/api/health/auth"
    static final AUTH_HEALTH_CHECK_PATH = BASE_PATH

    @Inject
    MockAuthenticationFetcher authenticationFetcher


    def "ヘルスチェックAPI(認証): 正常系 200 OKを返す"() {
        given:
        authenticationFetcher.authentication = Authentication.build(
            "1", [UserRole.PURCHASE_REQUEST_ADMIN.toString()]
        )

        expect:
        final request = this.getRequest(AUTH_HEALTH_CHECK_PATH)
        this.execute(request, HttpStatus.OK)
    }

    def "ヘルスチェックAPI(認証): 異常系 ユーザが事前に認証を受けていない場合は401エラー"() {
        expect:
        final request = this.getRequest(AUTH_HEALTH_CHECK_PATH)
        this.execute(request, HttpStatus.UNAUTHORIZED)
    }
}
