package cc.rits.membership.console.paymaster.enums

enum class UserRole(val id: Int) {
    /**
     * 購入申請の管理者
     */
    PAYMASTER_ADMIN(1);

    companion object {
        fun findById(id: Int): UserRole? {
            return UserRole.values().find {
                it.id == id
            }
        }
    }

}
