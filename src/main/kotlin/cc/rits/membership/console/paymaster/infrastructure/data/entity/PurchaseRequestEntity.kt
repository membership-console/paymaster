package cc.rits.membership.console.paymaster.infrastructure.data.entity

import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import java.time.LocalDateTime
import java.util.*

@MappedEntity(value = "purchase_request")
data class PurchaseRequestEntity(
    @Id
    val id: UUID,

    val name: String,

    val description: String,

    val price: Int,

    val quantity: Int,

    val url: String,

    val status: Int,

    val requestedBy: Int,

    val requestedAt: LocalDateTime
)
