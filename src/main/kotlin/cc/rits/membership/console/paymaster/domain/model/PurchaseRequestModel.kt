package cc.rits.membership.console.paymaster.domain.model

import cc.rits.membership.console.paymaster.enums.PurchaseRequestStatus
import java.time.LocalDateTime
import java.util.*

/**
 * 購入申請モデル
 */
class PurchaseRequestModel(
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
    val status: PurchaseRequestStatus,

    /***
     * 申請者
     */
    val requestedBy: UserModel?,

    /**
     * 申請日時
     */
    val requestedAt: LocalDateTime

)
