package cc.rits.membership.console.paymaster.enums

import cc.rits.membership.console.paymaster.BaseSpec

/**
 * 購入申請ステータスを単体テスト
 */
class PurchaseRequestStatus_UT extends BaseSpec {

    def "findById: idから購入申請ステータスを取得できる"() {
        when:
        final result = PurchaseRequestStatus.@Companion.findById(id)

        then:
        result == expectedResult

        where:
        id | expectedResult
        0  | PurchaseRequestStatus.PENDING_APPROVAL
        1  | PurchaseRequestStatus.APPROVED
        2  | PurchaseRequestStatus.PURCHASED
        3  | null
    }

}
