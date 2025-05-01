package ru.dayone.main.my_groups.data.network

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import ru.dayone.main.my_groups.data.network.models.CreateGroupRequestBody
import ru.dayone.main.my_groups.data.network.models.Group

interface MyGroupsRetrofitService {
    @GET("groups/{userId}/")
    fun getUserGroups(
        @Path("userId") userId: String
    ): Call<List<Group>>

    @POST("groups/")
    fun createGroup(
        @Body body: CreateGroupRequestBody
    ): Call<Group>
}