package ru.dayone.main.my_groups.data.datasource

import ru.dayone.main.my_groups.data.network.MyGroupsRetrofitService
import ru.dayone.main.my_groups.data.network.models.CreateGroupRequestBody
import ru.dayone.main.my_groups.data.network.models.Group
import ru.dayone.main.my_groups.domain.datasource.MyGroupsRemoteDataSource
import ru.dayone.tasksplitter.common.utils.Result
import ru.dayone.tasksplitter.common.utils.handle

class MyGroupsRemoteDataSourceImpl(
    private val service: MyGroupsRetrofitService
) : MyGroupsRemoteDataSource {
    override suspend fun getGroups(userId: String): Result<List<Group>> {
        return service.getUserGroups(userId).handle()
    }

    override suspend fun createGroup(
        userId: String,
        name: String
    ): Result<Group> {
        return service.createGroup(CreateGroupRequestBody(userId, name)).handle()
    }
}