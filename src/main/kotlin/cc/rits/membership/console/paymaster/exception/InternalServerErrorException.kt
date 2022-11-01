package cc.rits.membership.console.paymaster.exception

import io.micronaut.http.HttpStatus

class InternalServerErrorException(
    override val errorCode: ErrorCode
) : BaseException(errorCode, HttpStatus.INTERNAL_SERVER_ERROR)
