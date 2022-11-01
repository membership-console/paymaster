package cc.rits.membership.console.paymaster.infrastructure.api.controller

import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Status
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule

/**
 * ヘルスチェックコントローラー
 */
@Controller("/api/health")
@Secured(SecurityRule.IS_ANONYMOUS)
class HealthCheckRestController {

    /**
     * ヘルスチェックAPI
     */
    @Get
    @Status(HttpStatus.OK)
    fun check() {
    }

}
