package cc.rits.membership.console.paymaster.client.response

import com.fasterxml.jackson.annotation.JsonProperty
import io.micronaut.core.annotation.Introspected

/**
 * ユーザグループレスポンス
 */
@Introspected
data class UserGroupResponse(
    /**
     *ユーザグループID
     */
    @JsonProperty("id")
    val id: Int,

    /**
     * ユーザグループ名
     */
    @JsonProperty("name")
    val name: String,

    /**
     * ロールリスト
     */
    @JsonProperty("roles")
    val roles: List<Int>

)
