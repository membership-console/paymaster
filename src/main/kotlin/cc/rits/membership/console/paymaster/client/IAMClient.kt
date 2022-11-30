package cc.rits.membership.console.paymaster.client

import cc.rits.membership.console.paymaster.client.response.UserInfoResponse

interface IAMClient {

    fun getUserInfo(id: Int, accessToken: String): UserInfoResponse?

    fun getUserInfos(accessToken: String): List<UserInfoResponse>

}
