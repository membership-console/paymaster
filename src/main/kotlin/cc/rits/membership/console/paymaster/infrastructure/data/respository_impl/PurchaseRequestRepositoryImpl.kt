package cc.rits.membership.console.paymaster.infrastructure.data.respository_impl

import cc.rits.membership.console.paymaster.client.IAMClient
import cc.rits.membership.console.paymaster.domain.model.PurchaseRequestModel
import cc.rits.membership.console.paymaster.domain.repository.PurchaseRequestRepository
import cc.rits.membership.console.paymaster.enums.PurchaseRequestStatus
import cc.rits.membership.console.paymaster.exception.ErrorCode
import cc.rits.membership.console.paymaster.exception.InternalServerErrorException
import cc.rits.membership.console.paymaster.factory.UserFactory
import cc.rits.membership.console.paymaster.util.AuthUtil
import jakarta.inject.Singleton
import java.util.*

@Singleton
class PurchaseRequestRepositoryImpl(
    private val purchaseRequestQuery: PurchaseRequestQuery,
    private val iamClient: IAMClient,
    private val userFactory: UserFactory,
    private val authUtil: AuthUtil
) : PurchaseRequestRepository {

    override fun findById(id: UUID): PurchaseRequestModel? {
        return purchaseRequestQuery.findById(id)?.let {
            PurchaseRequestModel(
                id = it.id,
                name = it.name,
                description = it.description,
                price = it.price,
                quantity = it.quantity,
                url = it.url,
                status = PurchaseRequestStatus.findById(it.status) //
                    ?: throw InternalServerErrorException(ErrorCode.UNEXPECTED_ERROR),
                requestedBy = iamClient.getUserInfo(it.requestedBy, authUtil.getAccessToken())?.let { userInfo ->
                    userFactory.createModel(userInfo)
                },
                requestedAt = it.requestedAt
            )
        }
    }

    override fun findAll(): List<PurchaseRequestModel> {
        val purchaseRequestEntities = purchaseRequestQuery.find()
        purchaseRequestEntities.ifEmpty {
            return emptyList()
        }

        val userInfoResponses = iamClient.getUserInfos(authUtil.getAccessToken())
        return purchaseRequestEntities.map { purchaseRequest ->
            PurchaseRequestModel(
                id = purchaseRequest.id,
                name = purchaseRequest.name,
                description = purchaseRequest.description,
                price = purchaseRequest.price,
                quantity = purchaseRequest.quantity,
                url = purchaseRequest.url,
                status = PurchaseRequestStatus.findById(purchaseRequest.status) //
                    ?: throw InternalServerErrorException(ErrorCode.UNEXPECTED_ERROR),
                requestedBy = userInfoResponses.find { userInfo ->
                    userInfo.id == purchaseRequest.requestedBy
                }?.let {
                    userFactory.createModel(it)
                },
                requestedAt = purchaseRequest.requestedAt
            )
        }
    }

}
