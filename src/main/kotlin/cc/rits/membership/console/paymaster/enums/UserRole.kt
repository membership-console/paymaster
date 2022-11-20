package cc.rits.membership.console.paymaster.enums

enum class UserRole(private val id: Int) {

    /**
     * 購入申請の閲覧者
     */
    PURCHASE_REQUEST_VIEWER(2),

    /**
     * 購入申請の管理者
     */
    PURCHASE_REQUEST_ADMIN(3);

    companion object {
        fun findById(id: Int): UserRole? {
            return UserRole.values().find {
                it.id == id
            }
        }
    }

}
