package cc.rits.membership.console.paymaster

import io.micronaut.runtime.Micronaut.run
import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info

@OpenAPIDefinition(
    info = Info(
        title = "paymaster",
        version = "1.0.0-SNAPSHOT"
    )
)
object Api {
}

fun main(args: Array<String>) {
    run(*args)
}
