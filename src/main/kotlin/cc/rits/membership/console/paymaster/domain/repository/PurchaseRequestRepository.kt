package cc.rits.membership.console.paymaster.domain.repository

import cc.rits.membership.console.paymaster.domain.model.PurchaseRequestModel
import java.util.*

/**
 * 購入申請リポジトリ
 */
interface PurchaseRequestRepository {
    /**
     * 購入申請を登録
     */
    fun insert(purchaseRequestModel: PurchaseRequestModel)

    /**
     * idから購入申請を取得
     */
    fun findById(id: UUID): PurchaseRequestModel?

    /**
     * 全ての購入申請を取得
     */
    fun findAll(): List<PurchaseRequestModel>

}
