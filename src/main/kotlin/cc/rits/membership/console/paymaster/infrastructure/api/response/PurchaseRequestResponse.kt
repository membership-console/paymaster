package cc.rits.membership.console.paymaster.infrastructure.api.response

import cc.rits.membership.console.paymaster.domain.model.PurchaseRequestModel
import io.micronaut.core.annotation.Introspected
import java.time.LocalDateTime
import java.util.*

/**
 * 購入申請レスポンス
 */
@Introspected
data class PurchaseRequestResponse(
    /**
     * 購入申請ID
     */
    val id: UUID,

    /**
     * 申請品名
     */
    val name: String,

    /**
     * 説明
     */
    val description: String,

    /**
     * 価格
     */
    val price: Int,

    /**
     * 個数
     */
    val quantity: Int,

    /**
     * 申請品URL
     */
    val url: String,

    /**
     * 承認ステータス
     */
    val status: Int,

    /***
     * 申請者
     */
    val requestedBy: UserResponse?,

    /**
     * 申請日時
     */
    val requestedAt: LocalDateTime
) {
    constructor(purchaseRequestModel: PurchaseRequestModel) : this(
        id = purchaseRequestModel.id,
        name = purchaseRequestModel.name,
        description = purchaseRequestModel.description,
        price = purchaseRequestModel.price,
        quantity = purchaseRequestModel.quantity,
        url = purchaseRequestModel.url,
        status = purchaseRequestModel.status.id,
        requestedBy = purchaseRequestModel.requestedBy?.let {
            UserResponse(it)
        },
        requestedAt = purchaseRequestModel.requestedAt
    )
}
