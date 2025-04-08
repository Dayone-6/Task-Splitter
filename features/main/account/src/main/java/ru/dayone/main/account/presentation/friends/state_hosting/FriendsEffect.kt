package ru.dayone.main.account.presentation.friends.state_hosting

sealed class FriendsEffect{
    class StartLoading : FriendsEffect()
    class StopLoading : FriendsEffect()
    class FriendAdded : FriendsEffect()

}