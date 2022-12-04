package cc.rits.membership.console.paymaster.domain.model

import cc.rits.membership.console.paymaster.enums.UserRole

/**
 * ユーザモデル
 */
class UserModel(
    /**
     * ユーザID
     */
    val id: Int,

    /**
     * ファーストネーム
     */
    val firstName: String,

    /**
     * ラストネーム
     */
    val lastName: String,

    /**
     * 入学年度
     */
    val entranceYear: Int,

    /**
     * ロールリスト
     */
    val roles: List<UserRole>
)
