package cc.rits.membership.console.paymaster.client

import cc.rits.membership.console.paymaster.client.response.UserInfoResponse
import io.micronaut.http.cookie.Cookie

interface IAMClient {

    fun getUserInfo(cookies: Set<Cookie>): UserInfoResponse?

}
