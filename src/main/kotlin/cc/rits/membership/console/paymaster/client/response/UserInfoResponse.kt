package cc.rits.membership.console.paymaster.client.response

import io.micronaut.core.annotation.Introspected

/**
 * ユーザ情報レスポンス
 */
@Introspected
data class UserInfoResponse(
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
     * ユーザグループリスト
     */
    val userGroups: List<UserGroupResponse>

)
