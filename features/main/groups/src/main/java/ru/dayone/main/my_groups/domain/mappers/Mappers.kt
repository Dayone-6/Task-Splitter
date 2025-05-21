package ru.dayone.main.my_groups.domain.mappers

import ru.dayone.main.my_groups.domain.models.UserWithScore
import ru.dayone.tasksplitter.common.models.User

fun User.toUserWithScore(score: Int): UserWithScore?{
    return try {
        UserWithScore(
            this.id,
            this.name!!,
            this.nickname!!,
            score,
            this.color!!
        )
    }catch (e: Exception){
        e.printStackTrace()
        null
    }
}

fun UserWithScore.toUser(): User{
    return User(
        this.id,
        this.name,
        this.nickname,
        this.color
    )
}