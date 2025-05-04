package ru.dayone.main.my_groups.data.datasource

import ru.dayone.main.my_groups.data.network.GroupsRetrofitService
import ru.dayone.main.my_groups.data.network.models.CreateGroupRequestBody
import ru.dayone.main.my_groups.data.network.models.Group
import ru.dayone.main.my_groups.domain.datasource.GroupsRemoteDataSource
import ru.dayone.tasksplitter.common.utils.Result
import ru.dayone.tasksplitter.common.utils.handle
import javax.inject.Inject

class GroupsRemoteDataSourceImpl @Inject constructor(
    private val service: GroupsRetrofitService
) : GroupsRemoteDataSource {
    private var cachedGroups: List<Group>? = null

    override suspend fun getGroups(userId: String, requireNew: Boolean): Result<List<Group>> {
        if(cachedGroups == null || requireNew) {
            val result = service.getUserGroups(userId).handle()
            if(result is Result.Success){
                cachedGroups = result.result
            }
            return result
        }else{
            return Result.Success(cachedGroups!!)
        }
    }

    override suspend fun createGroup(
        userId: String,
        name: String
    ): Result<Group> {
        return service.createGroup(CreateGroupRequestBody(userId, name)).handle()
    }
}