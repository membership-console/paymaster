package cc.rits.membership.console.paymaster.infrastructure.api.request

import cc.rits.membership.console.paymaster.exception.BadRequestException
import cc.rits.membership.console.paymaster.exception.ErrorCode
import cc.rits.membership.console.paymaster.util.ValidationUtil
import io.micronaut.core.annotation.Introspected

/**
 * 購入申請作成リクエスト
 */
@Introspected
class UpsertPurchaseRequestRequest(
    /**
     * 申請品名
     */
    val name: String,

    /**
     * 説明
     */
    val description: String,

    /**
     * 単価
     */
    val price: Int,

    /**
     * 個数
     */
    val quantity: Int,

    /**
     * URL
     */
    val url: String

) : BaseRequest() {
    override fun validate() {
        if (!ValidationUtil.checkStringLength(name, 1, 255)) {
            throw BadRequestException(ErrorCode.INVALID_PURCHASE_REQUEST_NAME_LENGTH)
        }

        if (!ValidationUtil.checkStringLength(description, 1, 1023)) {
            throw BadRequestException(ErrorCode.INVALID_PURCHASE_REQUEST_DESCRIPTION_LENGTH)
        }

        if (!ValidationUtil.checkNumberLength(price, 1, Int.MAX_VALUE)) {
            throw BadRequestException(ErrorCode.INVALID_PURCHASE_REQUEST_PRICE)
        }

        if (!ValidationUtil.checkNumberLength(quantity, 1, Int.MAX_VALUE)) {
            throw BadRequestException(ErrorCode.INVALID_PURCHASE_REQUEST_QUANTITY)
        }

        if (!ValidationUtil.checkStringLength(url, 1, 1023)) {
            throw BadRequestException(ErrorCode.INVALID_PURCHASE_REQUEST_URL_LENGTH)
        }
    }
}
