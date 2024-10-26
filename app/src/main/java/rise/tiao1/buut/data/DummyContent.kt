package rise.tiao1.buut.data

import rise.tiao1.buut.data.remote.user.RemoteUser

object DummyContent {
    fun getDummyUsers() = arrayListOf(
        RemoteUser("auth0|6713adbf2d2a7c11375ac64c", "TestVoornaam1", "TestAchternaam1", "TestEmail1@hogent.be"),
        RemoteUser( "auth0|6713ad614fda04f4b9ae2156","TestVoornaam2", "TestAchternaam2", "TestEmail2@hogent.be"),
        RemoteUser("auth0|6713ad524e8a8907fbf0d57f", "TestVoornaam3", "TestAchternaam3", "TestEmail3@hogent.be")
    )

}