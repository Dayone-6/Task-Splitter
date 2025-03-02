package ru.dayone.auth.data.datasource

import android.content.Context
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import ru.dayone.auth.data.AuthType
import ru.dayone.auth.data.exception.NicknameIsAlreadyInUseException
import ru.dayone.auth.data.exception.NoSuchAuthTypeException
import ru.dayone.auth.data.exception.RequestCanceledException
import ru.dayone.auth.domain.datasource.AuthRemoteDataSource
import ru.dayone.tasksplitter.common.models.User
import ru.dayone.tasksplitter.common.utils.GOOGLE_AUTH_CLIENT_ID
import ru.dayone.tasksplitter.common.utils.Result
import ru.dayone.tasksplitter.common.utils.USERS_FIRESTORE_COLLECTION

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
        return try {
            val findNicknameTask =
                db.collection(USERS_FIRESTORE_COLLECTION).whereEqualTo("nickname", user.nickname)
                    .get()
            val findNicknameResult = findNicknameTask.await()
            if (findNicknameTask.isSuccessful) {
                if (!findNicknameResult.isEmpty) {
                    Result.Error(NicknameIsAlreadyInUseException())
                } else {
                    val signUpTask =
                        db.collection(USERS_FIRESTORE_COLLECTION).document(user.id).set(user)
                    val signUpResult = signUpTask.await()
                    if (signUpTask.isSuccessful) {
                        Result.Success(user)
                    } else {
                        Result.Error(RequestCanceledException())
                    }
                }
            } else {
                Result.Error(findNicknameTask.exception ?: Exception())
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    private suspend fun signInWithEmailAndPassword(credentials: AuthType.EmailAndPassword): Result<User> {
        val result: Result<User> = try {
            val signInTask =
                auth.signInWithEmailAndPassword(credentials.email, credentials.password)
            val signInResult = signInTask.await()
            if (signInTask.isSuccessful) {
                val registeredUser = getUserInformation(signInResult.user!!)
                Result.Success(registeredUser ?: User(signInResult.user!!.uid))
            } else {
                Result.Error(RequestCanceledException())
            }
        } catch (e: Exception) {
            if (e is FirebaseAuthInvalidCredentialsException) {
                val createUserResult = createFirebaseUserByEmailAndPassword(credentials)
                if (createUserResult is Result.Success) {
                    Result.Success(createUserResult.result)
                } else {
                    Result.Error(e)
                }
            } else {
                Result.Error(e)
            }
        }
        return result
    }

    private suspend fun createFirebaseUserByEmailAndPassword(credentials: AuthType.EmailAndPassword): Result<User> {
        val result: Result<User> = try {
            val signUpTask =
                auth.createUserWithEmailAndPassword(credentials.email, credentials.password)
            val signUpResult = signUpTask.await()
            if (signUpTask.isSuccessful) {
                Result.Success(User(signUpResult.user!!.uid))
            } else {
                Result.Error(RequestCanceledException())
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
        return result
    }

    private suspend fun signInWithPhone(credentials: AuthType.Phone): Result<User> {
        val result: Result<User> = try {
            val authTask = auth.signInWithCredential(credentials.credential)
            val authResult = authTask.await()
            if (authTask.isSuccessful) {
                val userData = getUserInformation(authResult.user!!)
                Result.Success(userData ?: User(authResult.user!!.uid))
            } else {
                Result.Error(RequestCanceledException())
            }
        } catch (e: Exception) {
            Result.Error(e)
        }

        return result
    }

    private suspend fun signInWithGoogle(credentials: AuthType.Google): Result<User> {
        val googleId = getGoogleId(credentials.context)
        val res: Result<User>
        if (googleId is Result.Success) {
            res = try {
                val firebaseCredential = GoogleAuthProvider.getCredential(googleId.result, null)
                val authTask = auth.signInWithCredential(firebaseCredential)
                val authResult = authTask.await()
                if (authTask.isSuccessful) {
                    val userData = getUserInformation(authResult.user!!)
                    Result.Success(userData ?: User(authResult.user!!.uid))
                } else {
                    Result.Error(RequestCanceledException())
                }
            } catch (e: Exception) {
                Result.Error(e)
            }
        } else {
            res = Result.Error((googleId as Result.Error).exception)
        }
        return res
    }

    private suspend fun getGoogleId(context: Context): Result<String> {
        val googleIdOptions = GetSignInWithGoogleOption.Builder(GOOGLE_AUTH_CLIENT_ID).build()

        val request: GetCredentialRequest = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOptions)
            .build()

        val result: Result<String> = try {
            val result = CredentialManager.create(context).getCredential(context, request)
            val credential = result.credential as CustomCredential
            if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                try {
                    val googleIdTokenCredential = GoogleIdTokenCredential
                        .createFrom(credential.data)
                    return Result.Success(googleIdTokenCredential.idToken)
                } catch (e: GoogleIdTokenParsingException) {
                    return Result.Error(e)
                }
            } else {
                Log.e("AuthMainContent", "Unexpected type of credential")
                return Result.Error(Exception())
            }
        } catch (e: Exception) {
            Result.Error(e)
        }

        return result
    }

    private suspend fun getUserInformation(user: FirebaseUser): User? {
        val task = db.collection(USERS_FIRESTORE_COLLECTION).document(user.uid).get()
        val result = try {
            val res = task.await()
            Log.d("AuthRemoteFirebaseDataSourceImpl", res.data.toString())
            if (task.isSuccessful) {
                res.toObject(User::class.java)
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
        return result
    }
}