package cc.rits.membership.console.paymaster.enums

import cc.rits.membership.console.paymaster.BaseSpec
import spock.lang.Unroll

/**
 * ユーザロールの単体テスト
 */
class UserRole_UT extends BaseSpec {

    @Unroll
    def "findById: idからロールを取得できる"() {
        when:
        final result = UserRole.@Companion.findById(id)

        then:
        result == expectedRole

        where:
        id | expectedRole
        2  | UserRole.VIEWER
        3  | UserRole.ADMIN
        4  | null
    }

}
