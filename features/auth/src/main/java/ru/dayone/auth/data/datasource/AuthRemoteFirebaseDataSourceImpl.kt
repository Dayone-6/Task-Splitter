package ru.dayone.auth.data.datasource

import android.util.Log
import androidx.compose.ui.platform.LocalGraphicsContext
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.asDeferred
import kotlinx.coroutines.tasks.await
import ru.dayone.auth.data.exception.NoSuchAuthTypeException
import ru.dayone.auth.data.exception.RequestCanceledException
import ru.dayone.auth.domain.AuthType
import ru.dayone.auth.domain.datasource.AuthRemoteDataSource
import ru.dayone.auth.domain.model.User
import ru.dayone.tasksplitter.common.utils.Result
import kotlin.math.sin

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
        val result: Result<User> = try {
            val signInTask = auth.signInWithEmailAndPassword(credentials.email, credentials.password)
            val signInResult = signInTask.await()
            if(signInTask.isSuccessful){
                val registeredUser = getUserInformation(signInResult.user!!)
                Result.Success(registeredUser ?: User(signInResult.user!!.uid))
            }else{
                Result.Error(RequestCanceledException())
            }
        }catch (e: Exception){
            if(e is FirebaseAuthInvalidCredentialsException){
                val createUserResult = createFirebaseUserByEmailAndPassword(credentials)
                if(createUserResult is Result.Success){
                    Result.Success(createUserResult.result)
                }else{
                    Result.Error(e)
                }
            }else{
                Result.Error(e)
            }
        }
        return result
    }

    private suspend fun createFirebaseUserByEmailAndPassword(credentials: AuthType.EmailAndPassword): Result<User> {
        val result: Result<User> = try {
            val signUpTask = auth.createUserWithEmailAndPassword(credentials.email, credentials.password)
            val signUpResult = signUpTask.await()
            if(signUpTask.isSuccessful){
                Result.Success(User(signUpResult.user!!.uid))
            }else{
                Result.Error(RequestCanceledException())
            }
        }catch (e: Exception){
            Result.Error(e)
        }
        return result
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