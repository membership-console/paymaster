package cc.rits.membership.console.paymaster.auth

import io.micronaut.context.annotation.Replaces
import io.micronaut.context.annotation.Requires
import io.micronaut.http.HttpRequest
import io.micronaut.runtime.context.scope.Refreshable
import io.micronaut.security.authentication.Authentication
import io.micronaut.security.filters.AuthenticationFetcher
import jakarta.inject.Singleton
import org.reactivestreams.Publisher
import org.slf4j.LoggerFactory
import reactor.core.publisher.Mono

@Requires(env = ["test"])
@Replaces(ApplicationAuthenticationFetcher::class)
@Refreshable
@Singleton
class MockAuthenticationFetcher : AuthenticationFetcher {

    private val logger = LoggerFactory.getLogger(MockAuthenticationFetcher::class.java)

    var authentication: Authentication? = null

    override fun fetchAuthentication(request: HttpRequest<*>?): Publisher<Authentication> {

        logger.info("Authentication Status: MOCK")

        return Mono.create { emitter ->
            if (authentication != null) {
                emitter.success(authentication)
            }
            emitter.success()
        }
    }

}
