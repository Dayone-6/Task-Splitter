package ru.dayone.main.my_groups.data.datasource

import ru.dayone.main.my_groups.data.network.MyGroupsRetrofitService
import ru.dayone.main.my_groups.data.network.models.CreateGroupRequestBody
import ru.dayone.main.my_groups.data.network.models.Group
import ru.dayone.main.my_groups.domain.datasource.MyGroupsRemoteDataSource
import ru.dayone.tasksplitter.common.utils.Result
import ru.dayone.tasksplitter.common.utils.handle
import javax.inject.Inject

class MyGroupsRemoteDataSourceImpl @Inject constructor(
    private val service: MyGroupsRetrofitService
) : MyGroupsRemoteDataSource {
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