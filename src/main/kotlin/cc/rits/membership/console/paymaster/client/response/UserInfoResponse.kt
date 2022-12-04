package cc.rits.membership.console.paymaster.client.response

import com.fasterxml.jackson.annotation.JsonProperty
import io.micronaut.core.annotation.Introspected

/**
 * ユーザ情報レスポンス
 */
@Introspected
data class UserInfoResponse(
    /**
     * ユーザID
     */
    @JsonProperty("id")
    val id: Int,

    /**
     * ファーストネーム
     */
    @JsonProperty("firstName")
    val firstName: String,

    /**
     * ラストネーム
     */
    @JsonProperty("lastName")
    val lastName: String,

    /**
     * 入学年度
     */
    @JsonProperty("entranceYear")
    val entranceYear: Int,

    /**
     * ユーザグループリスト
     */
    @JsonProperty("userGroups")
    val userGroups: List<UserGroupResponse>

)
