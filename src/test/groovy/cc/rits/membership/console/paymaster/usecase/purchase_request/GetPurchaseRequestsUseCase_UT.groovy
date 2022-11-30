package cc.rits.membership.console.paymaster.usecase.purchase_request

import cc.rits.membership.console.paymaster.BaseSpec
import cc.rits.membership.console.paymaster.domain.model.PurchaseRequestModel
import cc.rits.membership.console.paymaster.domain.repository.PurchaseRequestRepository
import cc.rits.membership.console.paymaster.helper.RandomHelper
import cc.rits.membership.console.paymaster.infrastructure.data.respository_impl.PurchaseRequestRepositoryImpl
import io.micronaut.test.annotation.MockBean
import jakarta.inject.Inject
/**
 * 購入申請リスト取得ユースケースの単体テスト
 */
class GetPurchaseRequestsUseCase_UT extends BaseSpec {

    @Inject
    GetPurchaseRequestsUseCase sut

    @Inject
    PurchaseRequestRepository purchaseRequestRepository

    @MockBean(PurchaseRequestRepositoryImpl)
    PurchaseRequestRepository purchaseRequestRepository() {
        return Mock(PurchaseRequestRepository)
    }

    def "handle: 購入申請リストを取得できる"() {
        given:
        final purchaseRequestModels = [RandomHelper.mock(PurchaseRequestModel), RandomHelper.mock(PurchaseRequestModel)]

        when:
        final result = this.sut.handle()

        then:
        1 * this.purchaseRequestRepository.findAll() >> purchaseRequestModels
        result == purchaseRequestModels

    }



}
