package cc.rits.membership.console.paymaster.exception

import io.micronaut.http.HttpStatus

class ConflictException(
    override val errorCode: ErrorCode
) : BaseException(errorCode, HttpStatus.CONFLICT)
