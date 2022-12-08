package cc.rits.membership.console.paymaster.usecase.purchase_request

import cc.rits.membership.console.paymaster.domain.model.PurchaseRequestModel
import cc.rits.membership.console.paymaster.domain.repository.PurchaseRequestRepository
import cc.rits.membership.console.paymaster.exception.ErrorCode
import cc.rits.membership.console.paymaster.exception.NotFoundException
import jakarta.inject.Singleton
import java.util.*
import javax.transaction.Transactional

/**
 * 購入申請取得ユースケース
 */
@Singleton
@Transactional
open class GetPurchaseRequestUseCase(
    private val purchaseRequestRepository: PurchaseRequestRepository
) {

    /**
     * 購入申請を取得する
     *
     * @param id 購入申請ID
     *
     * @return 購入申請
     */
    open fun handle(id: UUID): PurchaseRequestModel {
        return purchaseRequestRepository.findById(id) //
            ?: throw NotFoundException(ErrorCode.NOT_FOUND_PURCHASE_REQUEST)
    }

}
