package rise.tiao1.buut.user.presentation.register

import kotlinx.coroutines.delay
import rise.tiao1.buut.data.local.user.LocalUser
import rise.tiao1.buut.data.local.user.UserDao

class FakeUserDao : UserDao {
    private var users = mutableListOf<LocalUser>()

    override suspend fun insertUser(user: LocalUser) {
        delay(500)
        users.add(user)
    }

    override suspend fun getUserById(id: String): LocalUser? {
        delay(500)
        return users.find {dummyUser -> dummyUser.id == id}
    }

    override suspend fun updateUser(localUser: LocalUser) {
        delay(500)
        var toUpdate = users.find { dummyUser -> dummyUser.id == localUser.id}
        if (toUpdate != null) {
            users.remove(toUpdate)
            users.add(localUser)
        }
    }

    override suspend fun deleteUser(user: LocalUser) {
        delay(500)
        users.remove(user)
    }
}