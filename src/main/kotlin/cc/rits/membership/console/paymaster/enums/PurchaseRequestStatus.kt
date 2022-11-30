package cc.rits.membership.console.paymaster.enums

enum class PurchaseRequestStatus(val id: Int) {

    /**
     * 承認待ち
     */
    PENDING_APPROVAL(0),

    /**
     * 承認済み
     */
    APPROVED(1),

    /**
     * 購入済み
     */
    PURCHASED(2);

    companion object {
        fun findById(id: Int): PurchaseRequestStatus? {
            return PurchaseRequestStatus.values().find {
                it.id == id
            }
        }

    }
}
