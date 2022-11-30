package cc.rits.membership.console.paymaster.usecase.purchase_request

import cc.rits.membership.console.paymaster.domain.model.PurchaseRequestModel
import cc.rits.membership.console.paymaster.domain.repository.PurchaseRequestRepository
import jakarta.inject.Singleton
import javax.transaction.Transactional

/**
 * 購入申請取得ユースケース
 */
@Singleton
@Transactional
open class GetPurchaseRequestsUseCase(
    private val purchaseRequestRepository: PurchaseRequestRepository
) {

    /**
     * 購入申請リストを取得
     */
    open fun handle(): List<PurchaseRequestModel> {
        return purchaseRequestRepository.findAll()
    }

}
