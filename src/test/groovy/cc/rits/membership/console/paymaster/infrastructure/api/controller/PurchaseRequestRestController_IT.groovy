package cc.rits.membership.console.paymaster.infrastructure.api.controller

import cc.rits.membership.console.paymaster.client.IAMClient
import cc.rits.membership.console.paymaster.client.IAMClientImpl
import cc.rits.membership.console.paymaster.client.response.UserGroupResponse
import cc.rits.membership.console.paymaster.client.response.UserInfoResponse
import cc.rits.membership.console.paymaster.client.response.UserInfosResponse
import cc.rits.membership.console.paymaster.enums.PurchaseRequestStatus
import cc.rits.membership.console.paymaster.enums.UserRole
import cc.rits.membership.console.paymaster.exception.ErrorCode
import cc.rits.membership.console.paymaster.exception.UnauthorizedException
import cc.rits.membership.console.paymaster.helper.TableHelper
import cc.rits.membership.console.paymaster.infrastructure.api.response.PurchaseRequestsResponse
import io.micronaut.http.HttpStatus
import io.micronaut.security.oauth2.client.clientcredentials.ClientCredentialsClient
import io.micronaut.security.oauth2.endpoint.token.response.TokenResponse
import io.micronaut.test.annotation.MockBean
import jakarta.inject.Inject
import reactor.core.publisher.Mono
import spock.lang.Shared

class PurchaseRequestRestController_IT extends BaseRestController_IT {
    // API PATH
    static final BASE_PATH = "/api/purchase-requests"
    static final GET_PURCHASE_REQUESTS_API_PATH = BASE_PATH

    @Inject
    IAMClient iamClient

    @MockBean(IAMClientImpl)
    IAMClient iamClient() {
        return Mock(IAMClient)
    }

    @Inject
    ClientCredentialsClient clientCredentialsClient

    @MockBean(ClientCredentialsClient)
    ClientCredentialsClient clientCredentialsClient() {
        return Mock(ClientCredentialsClient)
    }

    @Shared
    TokenResponse tokenResponse

    @Shared
    UserInfosResponse userInfosResponse

    def setup() {
        this.tokenResponse = new TokenResponse("", "")
        this.userInfosResponse = new UserInfosResponse(
            [
                new UserInfoResponse(1, "", "", 2022, [
                    new UserGroupResponse(1, "", [UserRole.PAYMASTER_ADMIN.id])
                ]),
                new UserInfoResponse(2, "", "", 2022, [
                    new UserGroupResponse(1, "", [UserRole.PAYMASTER_ADMIN.id])
                ])
            ]
        )
    }

    def "購入申請リスト取得API: 正常系 購入申請リストを取得できる"() {
        given:
        final userInfoResponse = new UserInfoResponse(
            1, "test", "test", 2022, [
            new UserGroupResponse(1, "test", [UserRole.PAYMASTER_ADMIN.id])
        ])

        final request = this.getRequest(GET_PURCHASE_REQUESTS_API_PATH) //
            .header("Authorization", this.createAuthenticationInfo(userInfoResponse))

        final expectedIds = [
            UUID.randomUUID(),
            UUID.randomUUID(),
            UUID.randomUUID()
        ]

        // @formatter:off
        TableHelper.insert sql, "purchase_request", {
            id             | name | description | price | quantity | url | status                                    | requested_by
            expectedIds[0] | "A"  | ""          | 100   | 1        | ""  | PurchaseRequestStatus.PENDING_APPROVAL.id | 1
            expectedIds[1] | "B"  | ""          | 200   | 2        | ""  | PurchaseRequestStatus.APPROVED.id         | 2
            expectedIds[2] | "C"  | ""          | 300   | 3        | ""  | PurchaseRequestStatus.PURCHASED.id        | 3
        }
        // @formatter:on

        when:
        final response = this.execute(request, HttpStatus.OK, PurchaseRequestsResponse.class)

        then:
        1 * this.iamClient.getUserInfos("") >> this.userInfosResponse.users
        1 * this.clientCredentialsClient.requestToken() >> Mono.just(tokenResponse)

        final result = response.purchaseRequests
        result*.id == expectedIds
        result*.name == ["A", "B", "C"]
        result*.description == ["", "", ""]
        result*.price == [100, 200, 300]
        result*.quantity == [1, 2, 3]
        result*.url == ["", "", ""]
        result*.status == [PurchaseRequestStatus.PENDING_APPROVAL.id, PurchaseRequestStatus.APPROVED.id, PurchaseRequestStatus.PURCHASED.id]
        result*.requestedBy.id == [1, 2]
        result*.requestedBy.firstName == ["", ""]
        result*.requestedBy.lastName == ["", ""]
    }

    def "購入申請リスト取得API: 異常系 ログインしていない場合は401エラー"() {
        expect:
        final request = this.getRequest(GET_PURCHASE_REQUESTS_API_PATH)
        this.execute(request, new UnauthorizedException(ErrorCode.USER_NOT_LOGGED_IN))
    }
}
