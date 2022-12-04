package cc.rits.membership.console.paymaster.infrastructure.api.response

import cc.rits.membership.console.paymaster.domain.model.UserModel
import io.micronaut.core.annotation.Introspected

/**
 * ユーザレスポンス
 */
@Introspected
data class UserResponse(
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
    val lastName: String
) {
    constructor(userModel: UserModel) : this(userModel.id, userModel.firstName, userModel.lastName)
}
