package cc.rits.membership.console.paymaster.infrastructure.api.controller

import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Status
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import io.swagger.v3.oas.annotations.tags.Tag
import org.slf4j.LoggerFactory

/**
 * ヘルスチェックコントローラー
 */
@Tag(name = "health")
@Controller("/api/health")
@Secured(SecurityRule.IS_ANONYMOUS)
class HealthCheckRestController {

    private val logger = LoggerFactory.getLogger(HealthCheckRestController::class.java)

    /**
     * ヘルスチェックAPI
     */
    @Get
    @Status(HttpStatus.OK)
    fun check() {
        logger.info("Server status: OK")
    }

}
