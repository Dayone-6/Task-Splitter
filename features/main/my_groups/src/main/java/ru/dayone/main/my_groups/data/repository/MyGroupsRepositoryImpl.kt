package ru.dayone.main.my_groups.data.repository

import androidx.compose.material3.ExposedDropdownMenuBox
import ru.dayone.main.my_groups.domain.datasource.MyGroupsLocalDataSource
import ru.dayone.main.my_groups.domain.datasource.MyGroupsRemoteDataSource
import ru.dayone.main.my_groups.data.network.models.Group
import ru.dayone.main.my_groups.domain.repository.MyGroupsRepository
import ru.dayone.tasksplitter.common.utils.Result
import javax.inject.Inject

class MyGroupsRepositoryImpl @Inject constructor(
    private val localDataSource: MyGroupsLocalDataSource,
    private val remoteDataSource: MyGroupsRemoteDataSource
) : MyGroupsRepository {
    override suspend fun getMyGroups(requireNew: Boolean): Result<List<Group>> {
        try {
            val user = localDataSource.getUser()
            if(user == null){
                throw Exception()
            }
            return remoteDataSource.getGroups(user.id, requireNew)
        }catch (e: Exception){
            return Result.Error(e)
        }
    }

    override suspend fun createGroup(name: String): Result<Group> {
        try {
            val user = localDataSource.getUser()
            if(user == null){
                throw Exception()
            }
            return remoteDataSource.createGroup(user.id, name)
        }catch (e: Exception){
            return Result.Error(e)
        }
    }
}