package cc.rits.membership.console.paymaster.configuration

import io.micronaut.context.annotation.Property
import jakarta.inject.Singleton

/**
 * IAMのコンフィグレーション
 */
@Singleton
data class IAMConfiguration(

    @Property(name = "iam.api.host")
    val host: String

)

