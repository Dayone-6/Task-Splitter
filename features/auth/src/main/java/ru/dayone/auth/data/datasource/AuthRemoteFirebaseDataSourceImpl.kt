package ru.dayone.auth.data.datasource

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import ru.dayone.auth.data.exception.NoSuchAuthTypeException
import ru.dayone.auth.data.exception.RequestCanceledException
import ru.dayone.auth.domain.AuthType
import ru.dayone.auth.domain.datasource.AuthRemoteDataSource
import ru.dayone.auth.domain.model.User
import ru.dayone.tasksplitter.common.utils.Result

class AuthRemoteFirebaseDataSourceImpl(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore
) : AuthRemoteDataSource {

    override suspend fun signIn(type: AuthType): Result<User> {
        return when (type) {
            is AuthType.EmailAndPassword -> {
                signInWithEmailAndPassword(type)
            }

            is AuthType.Phone -> {
                signInWithPhone(type)
            }

            is AuthType.Google -> {
                signInWithGoogle(type)
            }

            else -> {
                throw NoSuchAuthTypeException()
            }
        }
    }

    override suspend fun signUp(user: User): Result<User> {
        return Result.Error(Exception())
    }

    private suspend fun signInWithEmailAndPassword(credentials: AuthType.EmailAndPassword): Result<User> {
        val result: User?
        try {
            var resultUser: FirebaseUser? = null
            auth.signInWithEmailAndPassword(credentials.email, credentials.password)
                .addOnSuccessListener {
                    resultUser = it.user ?: throw NullPointerException()
                }.addOnFailureListener {
                    throw it
                }.addOnCanceledListener {
                    throw RequestCanceledException()
                }.await()
            result = getUserInformation(resultUser!!) ?: User(resultUser!!.uid, null, null)
        } catch (e: Exception) {
            return Result.Error(e)
        }
        return Result.Success(result)
    }

    private fun signInWithPhone(credentials: AuthType.Phone): Result<User> {
        return Result.Error(NoSuchAuthTypeException())
    }

    private fun signInWithGoogle(credentials: AuthType.Google): Result<User> {
        return Result.Error(NoSuchAuthTypeException())
    }

    private suspend fun getUserInformation(user: FirebaseUser): User? {
        var result: User? = null
        db.collection("users").document(user.uid).get()
            .addOnSuccessListener {
                result = it.toObject(User::class.java)
            }.addOnFailureListener {
                throw it
            }.addOnCanceledListener {
                throw RequestCanceledException()
            }.await()
        return result
    }
}