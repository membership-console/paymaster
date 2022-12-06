package cc.rits.membership.console.paymaster.exception

/**
 * エラーコード
 */
enum class ErrorCode(val code: Int, val message: String) {
    /**
     * 30000~30999: 400 Bad Request
     */

    /**
     * 31000~31999: 401 Unauthorized
     */
    USER_NOT_LOGGED_IN(31000, "ユーザはログインしていません。問題が解決しない場合は、管理者までご連絡ください。"),

    /**
     * 32000~32999: 403 Forbidden
     */
    USER_HAS_NO_PERMISSION(32000, "その動作は許可されていません。"),

    /**
     * 33000~33999: 404 Not Found
     */
    NOT_FOUND_API(33000, "APIが見つかりません。"),

    NOT_FOUND_PURCHASE_REQUEST(33001, "購入申請が見つかりません。"),

    /**
     * 35000~35999: 500 Internal Server Error
     */
    UNEXPECTED_ERROR(35000, "予期しないエラーが発生しました。問題が解決しない場合は、管理者までご連絡ください。")

}
