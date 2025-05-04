package ru.dayone.main.my_groups.data.datasource

import android.content.SharedPreferences
import ru.dayone.main.my_groups.domain.datasource.GroupsLocalDataSource
import ru.dayone.tasksplitter.common.models.User
import ru.dayone.tasksplitter.common.utils.getUser
import javax.inject.Inject

class GroupsLocalDataSourceImpl @Inject constructor(
    private val sharedPrefs: SharedPreferences
) : GroupsLocalDataSource{
    override suspend fun getUser(): User? {
        return sharedPrefs.getUser()
    }
}