package ru.dayone.main.my_groups.data.datasource

import android.content.SharedPreferences
import ru.dayone.main.my_groups.domain.datasource.MyGroupsLocalDataSource
import ru.dayone.tasksplitter.common.models.User
import ru.dayone.tasksplitter.common.utils.getUser
import javax.inject.Inject

class MyGroupsLocalDataSourceImpl @Inject constructor(
    private val sharedPrefs: SharedPreferences
) : MyGroupsLocalDataSource{
    override suspend fun getUser(): User? {
        return sharedPrefs.getUser()
    }
}