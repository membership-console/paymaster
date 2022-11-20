package cc.rits.membership.console.paymaster.client.response

import io.micronaut.core.annotation.Introspected

/**
 * ユーザグループレスポンス
 */
@Introspected
data class UserGroupResponse(
    /**
     *ユーザグループID
     */
    val id: Int,

    /**
     * ユーザグループ名
     */
    val name: String,

    /**
     * ロールリスト
     */
    val roles: List<Int>

)
