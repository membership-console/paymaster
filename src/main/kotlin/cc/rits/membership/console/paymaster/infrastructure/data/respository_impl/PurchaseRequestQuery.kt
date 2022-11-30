package cc.rits.membership.console.paymaster.infrastructure.data.respository_impl

import cc.rits.membership.console.paymaster.infrastructure.data.entity.PurchaseRequestEntity
import io.micronaut.data.jdbc.annotation.JdbcRepository
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.repository.GenericRepository
import java.util.*

@JdbcRepository(dialect = Dialect.POSTGRES)
abstract class PurchaseRequestQuery : GenericRepository<PurchaseRequestEntity, UUID> {

    abstract fun findById(id: UUID): PurchaseRequestEntity?

    abstract fun find(): List<PurchaseRequestEntity>

}

