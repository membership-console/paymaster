package cc.rits.membership.console.paymaster.infrastructure.data.repository_impl

import cc.rits.membership.console.paymaster.BaseDatabaseSpec
import cc.rits.membership.console.paymaster.client.IAMClient
import cc.rits.membership.console.paymaster.client.IAMClientImpl
import cc.rits.membership.console.paymaster.client.response.UserGroupResponse
import cc.rits.membership.console.paymaster.client.response.UserInfoResponse
import cc.rits.membership.console.paymaster.domain.model.PurchaseRequestModel
import cc.rits.membership.console.paymaster.domain.model.UserModel
import cc.rits.membership.console.paymaster.domain.repository.PurchaseRequestRepository
import cc.rits.membership.console.paymaster.enums.PurchaseRequestStatus
import cc.rits.membership.console.paymaster.enums.UserRole
import cc.rits.membership.console.paymaster.factory.UserFactory
import cc.rits.membership.console.paymaster.helper.TableHelper
import io.micronaut.security.oauth2.client.clientcredentials.ClientCredentialsClient
import io.micronaut.security.oauth2.endpoint.token.response.TokenResponse
import io.micronaut.test.annotation.MockBean
import jakarta.inject.Inject
import reactor.core.publisher.Mono
import spock.lang.Shared

import java.time.LocalDateTime

/**
 * 購入申請リポジトリの単体テスト
 */
class PurchaseRequestRepositoryImpl_UT extends BaseDatabaseSpec {

    @Inject
    UserFactory userFactory

    @Inject
    PurchaseRequestRepository sut

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

    def setup() {
        this.tokenResponse = new TokenResponse("", "")
    }

    def "insert: 購入申請を登録できる"() {
        given:
        final userModel = new UserModel(1, "", "", 2022, [UserRole.PAYMASTER_ADMIN])
        final purchaseRequestModel =  new PurchaseRequestModel(
            UUID.randomUUID(), "", "", 1000, 1, "", PurchaseRequestStatus.PENDING_APPROVAL, userModel, LocalDateTime.now()
        )

        when:
        this.sut.insert(purchaseRequestModel)

        then:
        final result = sql.firstRow("SELECT * FROM purchase_request")
        result.id == purchaseRequestModel.id
        result.name == purchaseRequestModel.name
        result.description == purchaseRequestModel.description
        result.price == purchaseRequestModel.price
        result.quantity == purchaseRequestModel.quantity
        result.url == purchaseRequestModel.url
        result.status == purchaseRequestModel.status.id
        result.requested_by == purchaseRequestModel.requestedBy.id
    }

    def "findById: idから購入申請を取得できる"() {
        given:
        final expectedId = UUID.randomUUID()
        final userInfoResponse = new UserInfoResponse(1, "", "", 2022, [UserRole.PAYMASTER_ADMIN.toString()],
            [
                new UserGroupResponse(1, "", [UserRole.PAYMASTER_ADMIN.id])
            ]
        )
        final userModel = userFactory.createModel(userInfoResponse)

        // @formatter:off
        TableHelper.insert sql, "purchase_request", {
            id         | name | description | price | quantity | url | status                                    | requested_by
            expectedId | "A"  | ""          | 100   | 1        | ""  | PurchaseRequestStatus.PENDING_APPROVAL.id | 1
        }
        // @formatter:on

        when:
        final result = this.sut.findById(expectedId)

        then:
        1 * this.clientCredentialsClient.requestToken() >> Mono.just(tokenResponse)
        1 * this.iamClient.getUserInfo(1, "") >> userInfoResponse
        Objects.nonNull(result)
        result.id == expectedId
        result.name == "A"
        result.description == ""
        result.price == 100
        result.quantity == 1
        result.url == ""
        result.status == PurchaseRequestStatus.PENDING_APPROVAL
        result.requestedBy.id == userModel.id
        result.requestedBy.firstName == userModel.firstName
        result.requestedBy.lastName == userModel.lastName
        result.requestedBy.entranceYear == userModel.entranceYear
        result.requestedBy.roles == userModel.roles
        Objects.nonNull(result.requestedAt)
    }

    def "findById: 存在しない場合はnull"() {
        when:
        final result = this.sut.findById(UUID.randomUUID())

        then:
        Objects.isNull(result)
    }

    def "findAll: 全購入申請を取得できる"() {
        given:
        final expectedIds = [
            UUID.randomUUID(),
            UUID.randomUUID(),
            UUID.randomUUID()
        ]
        final userInfoResponseList = [
            new UserInfoResponse(1, "", "", 2022, [UserRole.PAYMASTER_ADMIN.toString()], [
                new UserGroupResponse(1, "", [UserRole.PAYMASTER_ADMIN.id])
            ]),
            new UserInfoResponse(2, "", "", 2022, [UserRole.PAYMASTER_ADMIN.toString()], [
                new UserGroupResponse(2, "", [UserRole.PAYMASTER_ADMIN.id])
            ])
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
        final result = this.sut.findAll()

        then:
        1 * this.clientCredentialsClient.requestToken() >> Mono.just(tokenResponse)
        1 * this.iamClient.getUserInfos("") >> userInfoResponseList
        result*.id == expectedIds
        result*.name == ["A", "B", "C"]
        result*.price == [100, 200, 300]
        result*.quantity == [1, 2, 3]
        result*.status == [PurchaseRequestStatus.PENDING_APPROVAL, PurchaseRequestStatus.APPROVED, PurchaseRequestStatus.PURCHASED]
        result*.requestedBy.size() == 3
        result*.requestedBy*.id == [1, 2, null]
        result*.requestedBy*.entranceYear == [2022, 2022, null]
        result*.requestedBy*.roles == [[UserRole.PAYMASTER_ADMIN], [UserRole.PAYMASTER_ADMIN], null]
    }

    def "findAll: 購入申請が一つも存在しない場合はempty"() {
        when:
        final result = this.sut.findAll()

        then:
        result.empty
    }

}
