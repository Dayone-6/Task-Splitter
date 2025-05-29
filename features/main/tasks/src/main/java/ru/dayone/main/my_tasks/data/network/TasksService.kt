package ru.dayone.main.my_tasks.data.network

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import ru.dayone.main.my_tasks.data.network.models.Group
import ru.dayone.main.my_tasks.data.network.models.Vote
import ru.dayone.main.my_tasks.data.network.models.VoteForTaskRequestBody
import ru.dayone.tasksplitter.common.models.Task

interface TasksService {
    @POST("/tasks/{taskId}/vote/")
    fun voteForTask(
        @Path("taskId") taskId: String,
        @Body body: VoteForTaskRequestBody
    ): Call<Vote>

    @GET("/tasks/{taskId}/votes/")
    fun getTaskVotes(
        @Path("taskId") taskId: String
    ): Call<List<Vote>>

    @POST("/tasks/{taskId}/end/")
    fun endTask(
        @Path("taskId") taskId: String
    ): Call<Task>

    @GET("/users/{userId}/tasks/")
    fun getUserTasks(
        @Path("userId") userId: String
    ): Call<List<Task>>

    @GET("/users/{userId}/tasks/completed/")
    fun getUserCompletedTasks(
        @Path("userId") userId: String
    ): Call<List<Task>>

    @GET("/groups/{groupId}/")
    fun getGroupById(
        @Path("groupId") groupId: String
    ): Call<Group>

    @POST("/tasks/{taskId}/pay/")
    fun payForTask(
        @Path("taskId") taskId: String
    ): Call<Task>
}