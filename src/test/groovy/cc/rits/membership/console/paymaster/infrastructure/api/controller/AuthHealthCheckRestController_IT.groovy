package cc.rits.membership.console.paymaster.infrastructure.api.controller

import cc.rits.membership.console.paymaster.client.response.UserGroupResponse
import cc.rits.membership.console.paymaster.client.response.UserInfoResponse
import cc.rits.membership.console.paymaster.enums.UserRole
import io.micronaut.http.HttpStatus

class AuthHealthCheckRestController_IT extends BaseRestController_IT {
    // API PATH
    static final BASE_PATH = "/api/health/auth"
    static final AUTH_HEALTH_CHECK_PATH = BASE_PATH

    def "ヘルスチェックAPI(認証): 正常系 200 OKを返す"() {
        given:
        final userInfoResponse = new UserInfoResponse(
            1, "test", "test", 2022, [UserRole.PAYMASTER_ADMIN.toString()], [
            new UserGroupResponse(1, "test", [2, 3])
        ])

        expect:
        final request = this.getRequest(AUTH_HEALTH_CHECK_PATH) //
            .header("X-Membership-Console-User", this.createAuthenticationInfo(userInfoResponse))
        this.execute(request, HttpStatus.OK)
    }

    def "ヘルスチェックAPI(認証): 異常系 ユーザが事前に認証を受けていない場合は401エラー"() {
        expect:
        final request = this.getRequest(AUTH_HEALTH_CHECK_PATH)
        this.execute(request, HttpStatus.UNAUTHORIZED)
    }
}
