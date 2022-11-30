package cc.rits.membership.console.paymaster.infrastructure.api.response

import io.micronaut.core.annotation.Introspected

/**
 * 購入申請リストレスポンス
 */
@Introspected
data class PurchaseRequestsResponse(
    /**
     * 購入申請リスト
     */
    val purchaseRequests: List<PurchaseRequestResponse>
)
