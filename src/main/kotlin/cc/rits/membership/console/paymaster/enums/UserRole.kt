package cc.rits.membership.console.paymaster.enums

enum class UserRole(val id: Int) {

    /**
     * 購入申請の閲覧者
     */
    VIEWER(2),

    /**
     * 購入申請の管理者
     */
    ADMIN(3);

    companion object {
        fun findById(id: Int): UserRole? {
            return UserRole.values().find {
                it.id == id
            }
        }
    }

}
