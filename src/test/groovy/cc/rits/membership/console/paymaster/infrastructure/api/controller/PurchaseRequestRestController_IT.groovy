package cc.rits.membership.console.paymaster.infrastructure.api.controller

import cc.rits.membership.console.paymaster.client.IAMClient
import cc.rits.membership.console.paymaster.client.IAMClientImpl
import cc.rits.membership.console.paymaster.client.response.UserGroupResponse
import cc.rits.membership.console.paymaster.client.response.UserInfoResponse
import cc.rits.membership.console.paymaster.client.response.UserInfosResponse
import cc.rits.membership.console.paymaster.enums.PurchaseRequestStatus
import cc.rits.membership.console.paymaster.enums.UserRole
import cc.rits.membership.console.paymaster.exception.BadRequestException
import cc.rits.membership.console.paymaster.exception.ErrorCode
import cc.rits.membership.console.paymaster.exception.NotFoundException
import cc.rits.membership.console.paymaster.exception.UnauthorizedException
import cc.rits.membership.console.paymaster.helper.TableHelper
import cc.rits.membership.console.paymaster.infrastructure.api.request.UpsertPurchaseRequestRequest
import cc.rits.membership.console.paymaster.infrastructure.api.response.PurchaseRequestResponse
import cc.rits.membership.console.paymaster.infrastructure.api.response.PurchaseRequestsResponse
import io.micronaut.http.HttpStatus
import io.micronaut.security.oauth2.client.clientcredentials.ClientCredentialsClient
import io.micronaut.security.oauth2.endpoint.token.response.TokenResponse
import io.micronaut.test.annotation.MockBean
import jakarta.inject.Inject
import reactor.core.publisher.Mono
import spock.lang.Shared
import spock.lang.Unroll

/**
 * 購入申請APIの統合テスト
 */
class PurchaseRequestRestController_IT extends BaseRestController_IT {
    // API PATH
    static final BASE_PATH = "/api/purchase-requests"
    static final CREATE_PURCHASE_REQUEST_API_PATH = BASE_PATH
    static final GET_PURCHASE_REQUEST_API_PATH = BASE_PATH + "/%s"
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
                new UserInfoResponse(1, "", "", 2022, [UserRole.PAYMASTER_ADMIN.toString()], [
                    new UserGroupResponse(1, "", [UserRole.PAYMASTER_ADMIN.id])
                ]),
                new UserInfoResponse(2, "", "", 2022, [UserRole.PAYMASTER_ADMIN.toString()], [
                    new UserGroupResponse(1, "", [UserRole.PAYMASTER_ADMIN.id])
                ])
            ]
        )
    }

    def "購入申請リスト取得API: 正常系 購入申請リストを取得できる"() {
        given:
        final userInfoResponse = new UserInfoResponse(
            1, "test", "test", 2022, [UserRole.PAYMASTER_ADMIN.toString()], [
            new UserGroupResponse(1, "test", [UserRole.PAYMASTER_ADMIN.id])
        ])

        final request = this.getRequest(GET_PURCHASE_REQUESTS_API_PATH) //
            .header("X-Membership-Console-User", this.createAuthenticationInfo(userInfoResponse))

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

    def "購入申請取得API: 正常系 購入申請を取得できる"() {
        given:
        final userInfoResponse = new UserInfoResponse(
            1, "test", "test", 2022, [UserRole.PAYMASTER_ADMIN.toString()], [
            new UserGroupResponse(1, "test", [UserRole.PAYMASTER_ADMIN.id])
        ])

        final purchaseRequestId = UUID.randomUUID()

        final request = this.getRequest(String.format(GET_PURCHASE_REQUEST_API_PATH, purchaseRequestId)) //
            .header("X-Membership-Console-User", this.createAuthenticationInfo(userInfoResponse))

        // @formatter:off
        TableHelper.insert sql, "purchase_request", {
            id                | name | description | price | quantity | url | status                                    | requested_by
            purchaseRequestId | "A"  | ""          | 100   | 1        | ""  | PurchaseRequestStatus.PENDING_APPROVAL.id | 1
        }
        // @formatter:on

        when:
        final result = this.execute(request, HttpStatus.OK, PurchaseRequestResponse.class)

        then:
        1 * this.iamClient.getUserInfo(1, "") >> userInfoResponse
        1 * this.clientCredentialsClient.requestToken() >> Mono.just(tokenResponse)

        result.id == purchaseRequestId
        result.name == "A"
        result.description == ""
        result.price == 100
        result.url == ""
        result.status == PurchaseRequestStatus.PENDING_APPROVAL.id
        result.requestedBy.id == userInfoResponse.id
        result.requestedBy.firstName == userInfoResponse.firstName
        result.requestedBy.lastName == userInfoResponse.lastName
    }

    def "購入申請取得API: 異常系 購入申請が存在しない場合は404エラー"() {
        given:
        final userInfoResponse = new UserInfoResponse(
            1, "test", "test", 2022, [UserRole.PAYMASTER_ADMIN.toString()], [
            new UserGroupResponse(1, "test", [UserRole.PAYMASTER_ADMIN.id])
        ])

        final purchaseRequestId = UUID.randomUUID()

        final request = this.getRequest(String.format(GET_PURCHASE_REQUEST_API_PATH, purchaseRequestId)) //
            .header("X-Membership-Console-User", this.createAuthenticationInfo(userInfoResponse))

        expect:
        this.execute(request, new NotFoundException(ErrorCode.NOT_FOUND_PURCHASE_REQUEST))
    }

    def "購入申請取得API: 異常系 ログインしていない場合は401エラー"() {
        expect:
        final request = this.getRequest(String.format(GET_PURCHASE_REQUEST_API_PATH, UUID.randomUUID()))
        this.execute(request, new UnauthorizedException(ErrorCode.USER_NOT_LOGGED_IN))
    }

    def "購入申請作成API: 正常系 購入申請を作成できる"() {
        given:
        final userInfoResponse = new UserInfoResponse(
            1, "test", "test", 2022, [UserRole.PAYMASTER_ADMIN.toString()], [
            new UserGroupResponse(1, "test", [UserRole.PAYMASTER_ADMIN.id])
        ])

        final upsertPurchaseRequestRequest = new UpsertPurchaseRequestRequest("A", "description", 1000, 1, "url")

        final request = this.postRequest(CREATE_PURCHASE_REQUEST_API_PATH, upsertPurchaseRequestRequest) //
            .header("X-Membership-Console-User", this.createAuthenticationInfo(userInfoResponse))

        expect:
        this.execute(request, HttpStatus.OK)
        final result = sql.firstRow("SELECT * FROM purchase_request")
        result.id != null
        result.name == upsertPurchaseRequestRequest.name
        result.description == upsertPurchaseRequestRequest.description
        result.price == upsertPurchaseRequestRequest.price
        result.quantity == upsertPurchaseRequestRequest.quantity
        result.url == upsertPurchaseRequestRequest.url
        result.status == PurchaseRequestStatus.PENDING_APPROVAL.id
        result.requested_by == 1
    }

    @Unroll
    def "購入申請作成API: 異常系 リクエストボディのバリデーション"() {
        given:
        final userInfoResponse = new UserInfoResponse(
            1, "test", "test", 2022, [UserRole.PAYMASTER_ADMIN.toString()], [
            new UserGroupResponse(1, "test", [UserRole.PAYMASTER_ADMIN.id])
        ])

        final upsertPurchaseRequestRequest = new UpsertPurchaseRequestRequest(name, description, price, quantity, url)

        final request = this.postRequest(CREATE_PURCHASE_REQUEST_API_PATH, upsertPurchaseRequestRequest) //
            .header("X-Membership-Console-User", this.createAuthenticationInfo(userInfoResponse))

        expect:
        this.execute(request, new BadRequestException(expectedErrorCode))

        where:
        name            | description      | price | quantity | url              || expectedErrorCode
        "A".repeat(500) | "description"    | 100   | 1        | "url"            || ErrorCode.INVALID_PURCHASE_REQUEST_NAME_LENGTH
        "A"             | "*".repeat(2000) | 100   | 1        | "url"            || ErrorCode.INVALID_PURCHASE_REQUEST_DESCRIPTION_LENGTH
        "A"             | "description"    | -1    | 1        | "url"            || ErrorCode.INVALID_PURCHASE_REQUEST_PRICE
        "A"             | "description"    | 100   | -1       | "url"            || ErrorCode.INVALID_PURCHASE_REQUEST_QUANTITY
        "A"             | "description"    | 100   | 1        | "*".repeat(2000) || ErrorCode.INVALID_PURCHASE_REQUEST_URL_LENGTH
    }

    def "購入申請作成API: 異常系 ログインしていない場合は401エラー"() {
        expect:
        final request = this.getRequest(String.format(CREATE_PURCHASE_REQUEST_API_PATH, _))
        this.execute(request, new UnauthorizedException(ErrorCode.USER_NOT_LOGGED_IN))
    }
}
