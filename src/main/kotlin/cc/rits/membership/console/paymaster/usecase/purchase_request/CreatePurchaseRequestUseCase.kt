package cc.rits.membership.console.paymaster.usecase.purchase_request

import cc.rits.membership.console.paymaster.client.response.UserInfoResponse
import cc.rits.membership.console.paymaster.domain.model.PurchaseRequestModel
import cc.rits.membership.console.paymaster.domain.model.UserModel
import cc.rits.membership.console.paymaster.domain.repository.PurchaseRequestRepository
import cc.rits.membership.console.paymaster.enums.PurchaseRequestStatus
import cc.rits.membership.console.paymaster.enums.UserRole
import cc.rits.membership.console.paymaster.infrastructure.api.request.UpsertPurchaseRequestRequest
import io.micronaut.security.authentication.Authentication
import jakarta.inject.Singleton
import javax.transaction.Transactional

/**
 * 購入申請作成ユースケース
 */
@Singleton
@Transactional
@Suppress("UNCHECKED_CAST")
open class CreatePurchaseRequestUseCase(
    private val purchaseRequestRepository: PurchaseRequestRepository
) {
    /**
     * 購入申請を作成
     *
     * @param authentication 認証情報
     * @param requestBody 購入申請作成リクエスト
     */
    open fun handle(authentication: Authentication, requestBody: UpsertPurchaseRequestRequest) {
        val userInfo = authentication.attributes["userInfo"] as UserInfoResponse
        val userModel = UserModel(
            id = userInfo.id,
            firstName = userInfo.firstName,
            lastName = userInfo.lastName,
            entranceYear = userInfo.entranceYear,
            roles = userInfo.userGroups.flatMap { userGroup ->
                userGroup.roles.mapNotNull {
                    UserRole.findById(it)
                }
            }
        )

        val purchaseRequestModel = PurchaseRequestModel(
            name = requestBody.name,
            description = requestBody.description,
            price = requestBody.price,
            quantity = requestBody.quantity,
            url = requestBody.url,
            status = PurchaseRequestStatus.PENDING_APPROVAL,
            requestedBy = userModel
        )

        purchaseRequestRepository.insert(purchaseRequestModel)
    }
}
