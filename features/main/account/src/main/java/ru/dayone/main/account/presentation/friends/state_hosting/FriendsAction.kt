package ru.dayone.main.account.presentation.friends.state_hosting

sealed class FriendsAction {
    class AddFriend(val friendId: String) : FriendsAction()
    class GetFriends(val userId: String) : FriendsAction()
    class SearchUsers(val userNickname: String) : FriendsAction()
    class GetUser() : FriendsAction()
}