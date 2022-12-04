package cc.rits.membership.console.paymaster.client

import cc.rits.membership.console.paymaster.client.response.UserInfoResponse
import cc.rits.membership.console.paymaster.client.response.UserInfosResponse
import cc.rits.membership.console.paymaster.configuration.IAMConfiguration
import cc.rits.membership.console.paymaster.exception.ErrorCode
import cc.rits.membership.console.paymaster.exception.InternalServerErrorException
import io.micronaut.core.type.Argument
import io.micronaut.http.HttpRequest
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.client.exceptions.HttpClientResponseException
import jakarta.inject.Singleton
import org.slf4j.LoggerFactory

@Singleton
class IAMClientImpl(
    @param:Client
    private val httpClient: HttpClient,
    private val iamConfiguration: IAMConfiguration
) : IAMClient {

    private val logger = LoggerFactory.getLogger(IAMClient::class.java)

    /**
     * ユーザ情報を取得
     *
     * @param id ユーザID
     * @param accessToken アクセストークン
     *
     * @return ユーザ情報レスポンス
     */
    override fun getUserInfo(id: Int, accessToken: String): UserInfoResponse? {
        val host = iamConfiguration.host
        try {
            val request = HttpRequest.GET<String>("$host/api/admin/users/${id}") //
                .header("Authorization", "Bearer $accessToken")
            val response = httpClient.toBlocking().exchange(request, Argument.of(UserInfoResponse::class.java))
            return response?.body()
        } catch (exception: HttpClientResponseException) {
            logger.error(exception.message)
            throw InternalServerErrorException(ErrorCode.UNEXPECTED_ERROR)
        }
    }

    /**
     * 全ユーザ情報を取得
     *
     * @param accessToken アクセストークン　
     *
     * @return ユーザ情報レスポンスリスト
     */
    override fun getUserInfos(accessToken: String): List<UserInfoResponse> {
        val host = iamConfiguration.host
        try {
            val request = HttpRequest.GET<String>("$host/api/admin/users") //
                .header("Authorization", "Bearer $accessToken")
            val response = httpClient.toBlocking().exchange(request, Argument.of(UserInfosResponse::class.java))
            val userInfosResponse = response.body() ?: return emptyList()
            return userInfosResponse.users
        } catch (exception: HttpClientResponseException) {
            logger.error(exception.message)
            throw InternalServerErrorException(ErrorCode.UNEXPECTED_ERROR)
        }
    }

}
