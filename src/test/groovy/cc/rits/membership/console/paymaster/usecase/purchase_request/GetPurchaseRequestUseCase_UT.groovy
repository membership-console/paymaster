package cc.rits.membership.console.paymaster.usecase.purchase_request

import cc.rits.membership.console.paymaster.BaseSpec
import cc.rits.membership.console.paymaster.domain.model.PurchaseRequestModel
import cc.rits.membership.console.paymaster.domain.repository.PurchaseRequestRepository
import cc.rits.membership.console.paymaster.exception.BaseException
import cc.rits.membership.console.paymaster.exception.ErrorCode
import cc.rits.membership.console.paymaster.exception.NotFoundException
import cc.rits.membership.console.paymaster.helper.RandomHelper
import cc.rits.membership.console.paymaster.infrastructure.data.respository_impl.PurchaseRequestRepositoryImpl
import io.micronaut.test.annotation.MockBean
import jakarta.inject.Inject

/**
 * 購入申請取得ユースケースの単体テスト
 */
class GetPurchaseRequestUseCase_UT extends BaseSpec {

    @Inject
    GetPurchaseRequestUseCase sut

    @Inject
    PurchaseRequestRepository purchaseRequestRepository

    @MockBean(PurchaseRequestRepositoryImpl)
    PurchaseRequestRepository purchaseRequestRepository() {
        return Mock(PurchaseRequestRepository)
    }

    def "handle: 購入申請を取得できる"() {
        given:
        final purchaseRequestModel = RandomHelper.mock(PurchaseRequestModel)

        when:
        final result = this.sut.handle(purchaseRequestModel.id)

        then:
        1 * this.purchaseRequestRepository.findById(purchaseRequestModel.id) >> purchaseRequestModel
        result == purchaseRequestModel
    }

    def "handle: 存在しない場合は404エラー"() {
        given:
        final id = UUID.randomUUID()

        when:
        this.sut.handle(id)

        then:
        1 * this.purchaseRequestRepository.findById(id) >> null
        final BaseException exception = thrown()
        verifyException(exception, new NotFoundException(ErrorCode.NOT_FOUND_PURCHASE_REQUEST))
    }

}
