package cc.rits.membership.console.paymaster.infrastructure.api.controller

import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Status
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import io.swagger.v3.oas.annotations.tags.Tag
import org.slf4j.LoggerFactory

@Tag(name = "health")
@Controller("/api/health/auth")
@Secured(SecurityRule.IS_AUTHENTICATED)
class AuthHealthCheckRestController {

    private val logger = LoggerFactory.getLogger(AuthHealthCheckRestController::class.java)

    @Get
    @Status(HttpStatus.OK)
    fun check() {
        logger.info("The connection for authentication server status: OK")
    }

}
