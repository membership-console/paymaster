package cc.rits.membership.console.paymaster.exception

import io.micronaut.http.HttpStatus

/**
 * エラーコード
 */
enum class ErrorCode(val code: Int, val message: String) {

    /**
     * 500
     */
    UNKNOWN_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.code, "不明なエラーが発生しました。問題が解決しない場合は管理者にお問い合わせください。"),

    UNEXPECTED_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.code, "予期せぬエラーが発生しました。今一度操作を見直してください。")

}
