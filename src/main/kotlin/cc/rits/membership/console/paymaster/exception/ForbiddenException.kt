package cc.rits.membership.console.paymaster.exception

import io.micronaut.http.HttpStatus

class ForbiddenException(
    override val errorCode: ErrorCode
) : BaseException(errorCode, HttpStatus.FORBIDDEN)
