package ru.dayone.main.my_groups.data.network

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import ru.dayone.main.my_groups.data.network.models.AddMemberToGroupRequestBody
import ru.dayone.main.my_groups.data.network.models.CreateGroupRequestBody
import ru.dayone.main.my_groups.data.network.models.CreateTaskRequestBody
import ru.dayone.main.my_groups.data.network.models.Group
import ru.dayone.main.my_groups.data.network.models.GroupMember
import ru.dayone.main.my_groups.data.network.models.Task
import ru.dayone.main.my_groups.data.network.models.UserFriend

interface GroupsRetrofitService {
    @GET("groups/{userId}/")
    fun getUserGroups(
        @Path("userId") userId: String
    ): Call<List<Group>>

    @POST("groups/")
    fun createGroup(
        @Body body: CreateGroupRequestBody
    ): Call<Group>

    @GET("groups/{groupId}/tasks/")
    fun getGroupTasks(
        @Path("groupId") groupId: String
    ): Call<List<Task>>

    @POST("groups/{groupId}/members/")
    fun addMemberToGroup(
        @Path("groupId") groupId: String,
        @Body body: AddMemberToGroupRequestBody
    ): Call<GroupMember>

    @GET("users/{userId}/friends/")
    fun getUserFriends(
        @Path("userId") userId: String
    ): Call<List<UserFriend>>

    @POST("/groups/{groupId}/tasks/")
    fun createTask(
        @Path("groupId") groupId: String,
        @Body body: CreateTaskRequestBody
    ): Call<Task>
}