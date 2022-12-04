package cc.rits.membership.console.paymaster.factory

import cc.rits.membership.console.paymaster.client.response.UserInfoResponse
import cc.rits.membership.console.paymaster.domain.model.UserModel
import cc.rits.membership.console.paymaster.enums.UserRole
import jakarta.inject.Singleton

@Singleton
class UserFactory {

    fun createModel(userInfoResponse: UserInfoResponse): UserModel {
        return UserModel(
            userInfoResponse.id,
            userInfoResponse.firstName,
            userInfoResponse.lastName,
            userInfoResponse.entranceYear,
            userInfoResponse.userGroups.flatMap {userGroup ->
                userGroup.roles.mapNotNull {
                    UserRole.findById(it)
                }
            }
        )
    }

}
