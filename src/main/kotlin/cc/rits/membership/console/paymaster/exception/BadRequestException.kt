package cc.rits.membership.console.paymaster.exception

import io.micronaut.http.HttpStatus

class BadRequestException(
    override val errorCode: ErrorCode
) : BaseException(errorCode, HttpStatus.BAD_REQUEST)
