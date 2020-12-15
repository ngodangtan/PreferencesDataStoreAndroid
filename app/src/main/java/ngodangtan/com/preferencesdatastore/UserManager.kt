package ngodangtan.com.preferencesdatastore

import android.content.Context
import android.widget.Toast
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserManager(context: Context) {

    private val dataStore = context.createDataStore(name = "user_prefs")

    companion object {
        val USER_NAME_KEY = preferencesKey<String>("USER_NAME")
        val USER_AGE_KEY = preferencesKey<Int>("USER_AGE")
        val USER_GENDER_KEY = preferencesKey<Boolean>("USER_GENDER")

    }

    suspend fun storeUser(age:Int, name: String, isMale: Boolean){
        dataStore.edit {
            it[USER_NAME_KEY] = name
            it[USER_AGE_KEY] = age
            it[USER_GENDER_KEY] = isMale
        }
    }

    val userNameFlow: Flow<String> = dataStore.data.map {
        it[USER_NAME_KEY] ?:""

    }

    val userAgeFlow: Flow<Int> = dataStore.data.map {
        val age = it[USER_AGE_KEY] ?:0

        if(age < 18){
            Toast.makeText(context,"The user is under 18",Toast.LENGTH_LONG).show()
        }
        age
    }

    val userGenderFlow: Flow<Boolean> = dataStore.data.map {
        it[USER_GENDER_KEY] ?: false
    }

}