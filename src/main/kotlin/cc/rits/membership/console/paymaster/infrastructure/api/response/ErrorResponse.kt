package cc.rits.membership.console.paymaster.infrastructure.api.response

import io.micronaut.core.annotation.Introspected

/**
 * エラーレスポンス
 */
@Introspected
data class ErrorResponse(val code: Int, val message: String)
