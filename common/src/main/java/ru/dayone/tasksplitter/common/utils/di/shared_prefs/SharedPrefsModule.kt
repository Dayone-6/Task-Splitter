package ru.dayone.tasksplitter.common.utils.di.shared_prefs

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import dagger.Module
import dagger.Provides
import ru.dayone.tasksplitter.common.utils.ENCRYPTED_SHARED_PREFS_NAME
import javax.inject.Singleton

@Module
class SharedPrefsModule{

    @Provides
    @EncryptedSharedPrefsQualifier
    @Singleton
    fun provideEncryptedSharedPrefs(context: Context): SharedPreferences{
        val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
        return EncryptedSharedPreferences.create(
            ENCRYPTED_SHARED_PREFS_NAME,
            masterKeyAlias,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }
}