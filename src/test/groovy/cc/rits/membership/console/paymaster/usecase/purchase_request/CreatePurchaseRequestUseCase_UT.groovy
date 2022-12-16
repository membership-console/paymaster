package cc.rits.membership.console.paymaster.usecase.purchase_request

import cc.rits.membership.console.paymaster.BaseSpec
import cc.rits.membership.console.paymaster.client.response.UserInfoResponse
import cc.rits.membership.console.paymaster.domain.repository.PurchaseRequestRepository
import cc.rits.membership.console.paymaster.helper.RandomHelper
import cc.rits.membership.console.paymaster.infrastructure.api.request.UpsertPurchaseRequestRequest
import cc.rits.membership.console.paymaster.infrastructure.data.respository_impl.PurchaseRequestRepositoryImpl
import io.micronaut.security.authentication.Authentication
import io.micronaut.test.annotation.MockBean
import jakarta.inject.Inject

/**
 * 購入申請作成ユースケースの単体テスト
 */
class CreatePurchaseRequestUseCase_UT extends BaseSpec {

    @Inject
    CreatePurchaseRequestUseCase sut

    @Inject
    PurchaseRequestRepository purchaseRequestRepository

    @MockBean(PurchaseRequestRepositoryImpl)
    PurchaseRequestRepository purchaseRequestRepository() {
        return Mock(PurchaseRequestRepository)
    }

    def "handle: 購入申請を作成できる"() {
        given:
        final authentication = Authentication.build("1", [], Map.of("userInfo", RandomHelper.mock(UserInfoResponse)))
        final upsertPurchaseRequestRequest = RandomHelper.mock(UpsertPurchaseRequestRequest)

        when:
        this.sut.handle(authentication, upsertPurchaseRequestRequest)

        then:
        1 * this.purchaseRequestRepository.insert(_)
        noExceptionThrown()
    }
}
