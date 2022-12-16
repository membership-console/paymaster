package cc.rits.membership.console.paymaster.util

import io.micronaut.core.util.StringUtils

/**
 * バリデーションユーティリティ
 */
class ValidationUtil {
    companion object {
        /**
         * 文字列の長さが範囲に収まるかチェック
         *
         * @param string 文字列
         * @param min 最小値
         * @param max 最大値
         *
         * @return バリデーション結果
         */
        fun checkStringLength(string: String, min: Int, max: Int): Boolean {
            if (StringUtils.isEmpty(string) && min != 0) {
                return false
            }

            return string.length in min..max
        }

        /**
         * 数値が範囲に収まるかチェック
         *
         * @param value 数値
         * @param min 最小値
         * @param max 最大値
         *
         * @return バリデーション結果
         */
        fun checkNumberLength(value: Int, min: Int, max: Int): Boolean {
            return value in min..max
        }
    }
}
