package cc.rits.membership.console.paymaster.util

import cc.rits.membership.console.paymaster.BaseSpec
import cc.rits.membership.console.paymaster.helper.RandomHelper
import spock.lang.Unroll

/**
 * バリデーションユーティリティの単体テスト
 */
class ValidationUtil_UT extends BaseSpec {

    @Unroll
    def "checkStringLength: 文字列の長さが範囲に収まるかチェックできる"() {
        when:
        final result = ValidationUtil.@Companion.checkStringLength(string, min, max)

        then:
        result == expectedResult

        where:
        string                        | min | max || expectedResult
        ""                            | 1   | 10  || false
        ""                            | 0   | 10  || true
        RandomHelper.alphanumeric(3)  | 1   | 10  || true
        RandomHelper.alphanumeric(11) | 1   | 10  || false
    }

    @Unroll
    def "checkNumberLength: 数値が範囲に収まるかチェックできる"() {
        when:
        final result = ValidationUtil.@Companion.checkNumberLength(value, min, max)

        then:
        result == expectedResult

        where:
        value | min | max || expectedResult
        0     | 1   | 10  || false
        3     | 1   | 10  || true
        11    | 1   | 10  || false
    }

}
