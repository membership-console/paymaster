package cc.rits.membership.console.paymaster.infrastructure.api.controller

import cc.rits.membership.console.paymaster.infrastructure.api.response.PurchaseRequestResponse
import cc.rits.membership.console.paymaster.infrastructure.api.response.PurchaseRequestsResponse
import cc.rits.membership.console.paymaster.usecase.purchase_request.GetPurchaseRequestsUseCase
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.scheduling.TaskExecutors
import io.micronaut.scheduling.annotation.ExecuteOn
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import io.swagger.v3.oas.annotations.tags.Tag

/**
 * 購入申請コントローラー
 */
@Tag(name = "purchase requests", description = "購入申請")
@Controller("/api/purchase-requests")
@Secured(SecurityRule.IS_AUTHENTICATED)
@ExecuteOn(TaskExecutors.IO)
class PurchaseRequestRestController(
    private val purchaseRequestsUseCase: GetPurchaseRequestsUseCase
) {

    /**
     * 購入申請リスト取得API
     *
     * @return 購入申請リスト
     */
    @Get
    fun getPurchaseRequests(): PurchaseRequestsResponse {
        val purchaseRequests = purchaseRequestsUseCase.handle().map {
            PurchaseRequestResponse(it)
        }
        return PurchaseRequestsResponse(purchaseRequests)
    }
}
