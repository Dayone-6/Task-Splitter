package ru.dayone.main.my_groups.data.datasource

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import ru.dayone.main.my_groups.data.network.GroupsRetrofitService
import ru.dayone.main.my_groups.data.network.models.AddMemberToGroupRequestBody
import ru.dayone.main.my_groups.data.network.models.CreateGroupRequestBody
import ru.dayone.main.my_groups.data.network.models.CreateTaskRequestBody
import ru.dayone.main.my_groups.data.network.models.Group
import ru.dayone.main.my_groups.data.network.models.GroupMember
import ru.dayone.tasksplitter.common.models.Task
import ru.dayone.main.my_groups.domain.datasource.GroupsRemoteDataSource
import ru.dayone.main.my_groups.presentation.my_groups.state_hosting.MyGroupsEffect
import ru.dayone.tasksplitter.common.exceptions.RequestCanceledException
import ru.dayone.tasksplitter.common.models.User
import ru.dayone.tasksplitter.common.utils.Result
import ru.dayone.tasksplitter.common.utils.USERS_FIRESTORE_COLLECTION
import ru.dayone.tasksplitter.common.utils.handle
import javax.inject.Inject

class GroupsRemoteDataSourceImpl @Inject constructor(
    private val service: GroupsRetrofitService,
    private val db: FirebaseFirestore
) : GroupsRemoteDataSource {
    private var cachedGroups: List<Group>? = null

    private var cachedGroupTasks: HashMap<String, List<Task>> = hashMapOf<String, List<Task>>()

    override suspend fun getGroups(userId: String, requireNew: Boolean): Result<List<Group>> {
        if (cachedGroups == null || requireNew) {
            val result = service.getUserGroups(userId).handle()
            if (result is Result.Success) {
                cachedGroups = result.result
            }
            return result
        } else {
            return Result.Success(cachedGroups!!)
        }
    }

    override suspend fun createGroup(
        userId: String,
        name: String
    ): Result<Group> {
        return service.createGroup(CreateGroupRequestBody(userId, name)).handle()
    }

    override suspend fun getGroupTasks(groupId: String, requireNew: Boolean): Result<List<Task>> {
        if (groupId in cachedGroupTasks && !requireNew) {
            return Result.Success(cachedGroupTasks[groupId]!!)
        }
        val result = service.getGroupTasks(groupId).handle()
        if (result is Result.Success) {
            cachedGroupTasks[groupId] = result.result
        }
        return result
    }

    override suspend fun addMemberToGroup(
        groupId: String,
        memberId: String
    ): Result<GroupMember> {
        return service.addMemberToGroup(groupId, AddMemberToGroupRequestBody(memberId)).handle()
    }

    override suspend fun getUserFromGroupMember(groupMember: GroupMember): Result<User> {
        val task = db.collection(USERS_FIRESTORE_COLLECTION).document(groupMember.memberId).get()
        val result = task.await()
        return if (task.isSuccessful) {
            Result.Success(result.toObject(User::class.java)!!)
        } else if (task.isCanceled) {
            Result.Error(RequestCanceledException())
        } else {
            Result.Error(task.exception!!)
        }
    }

    override suspend fun getUserFriends(userId: String): Result<List<User>> {
        return try {
            val friendIdsResult = service.getUserFriends(userId).handle()
            Log.d("AccountRemoteDataSource", friendIdsResult.toString())
            if (friendIdsResult is Result.Success) {
                if (friendIdsResult.result.isNotEmpty()) {
                    val task = db.collection(USERS_FIRESTORE_COLLECTION)
                        .whereIn("id", friendIdsResult.result.map {
                            if (userId != it.friendId) {
                                it.friendId
                            } else {
                                it.userId
                            }
                        }).get()
                    val taskResult = task.await()
                    val result: Result<List<User>> = if (task.isSuccessful) {
                        Result.Success(taskResult.toObjects(User::class.java))
                    } else if (task.isCanceled) {
                        Result.Error(RequestCanceledException())
                    } else if (task.exception != null) {
                        Result.Error(task.exception!!)
                    } else {
                        Result.Error(Exception())
                    }
                    return result
                } else {
                    return Result.Success(emptyList())
                }
            } else {
                return Result.Error((friendIdsResult as Result.Error).exception)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(e)
        }
    }

    override suspend fun createTask(
        groupId: String,
        title: String,
        description: String
    ): Result<Task> {
        return service.createTask(groupId, CreateTaskRequestBody(title, description)).handle()
    }
}