package cc.rits.membership.console.paymaster

import groovy.sql.Sql
import io.micronaut.transaction.jdbc.DelegatingDataSource
import jakarta.inject.Inject

import javax.sql.DataSource

class BaseDatabaseSpec extends BaseSpec {

    @Inject
    DataSource dataSource

    /**
     * SQL Handler
     */
    Sql sql

    @Inject
    private init() {
        if (Objects.isNull(sql)) {
            sql = Sql.newInstance((dataSource as DelegatingDataSource).targetDataSource)
        }
    }

    /**
     * setup before test class
     */
    def setup() {
        final timeZone = TimeZone.getTimeZone("Asia/Tokyo")
        TimeZone.setDefault(timeZone)
    }

    /**
     * cleanup after test case
     */
    def cleanup() {
        sql.execute("DELETE FROM purchase_request")
    }

}
