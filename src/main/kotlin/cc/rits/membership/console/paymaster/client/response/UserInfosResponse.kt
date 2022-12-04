package cc.rits.membership.console.paymaster.client.response

import com.fasterxml.jackson.annotation.JsonProperty
import io.micronaut.core.annotation.Introspected

@Introspected
data class UserInfosResponse(
    @JsonProperty("users")
    val users: List<UserInfoResponse>
)
