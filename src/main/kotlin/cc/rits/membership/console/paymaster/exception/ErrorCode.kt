package cc.rits.membership.console.paymaster.exception

/**
 * エラーコード
 */
enum class ErrorCode(val code: Int, val message: String) {
    /**
     * 30000~30999: 400 Bad Request
     */
    INVALID_PURCHASE_REQUEST_NAME_LENGTH(30000, "購入申請品名は1文字以上255文字以下である必要があります。"),

    INVALID_PURCHASE_REQUEST_DESCRIPTION_LENGTH(30001, "購入申請の説明文は1文字以上1023文字以下である必要があります。"),

    INVALID_PURCHASE_REQUEST_PRICE(30002, "購入申請品の単価は1円以上である必要があります。"),

    INVALID_PURCHASE_REQUEST_QUANTITY(30003, "購入申請品の個数は1個以上である必要があります。"),

    INVALID_PURCHASE_REQUEST_URL_LENGTH(30004, "購入申請品名のURLは1文字以上1023文字以下である必要があります。"),

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
