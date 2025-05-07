package ru.dayone.main.account.data.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import ru.dayone.main.account.data.network.models.UserFriend

private const val FRIENDS_URL = "users/{userId}/friends/"

interface AccountRetrofitService {
    @GET(FRIENDS_URL)
    fun getUserFriends(
        @Path("userId") userId: String
    ): Call<List<UserFriend>>

    @POST(FRIENDS_URL)
    fun addFriend(
        @Path("userId") userId: String,
        @Query("friendId") friendId: String
    ): Call<UserFriend>

}