package ua.nure.verenych.sportmanager.data.prefs

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREFS_NAME  = "sport_manager_session"
        private const val KEY_TOKEN   = "jwt_token"
        private const val KEY_USER_ID = "user_id"
        private const val KEY_ROLE    = "role"
        private const val KEY_FIRST   = "first_name"
        private const val KEY_LAST    = "last_name"
        private const val KEY_TEAM_ID = "team_id"
    }

    fun saveSession(token: String, userId: Int, role: String,
                    firstName: String, lastName: String, teamId: Int?) {
        prefs.edit()
            .putString(KEY_TOKEN, token)
            .putInt(KEY_USER_ID, userId)
            .putString(KEY_ROLE, role)
            .putString(KEY_FIRST, firstName)
            .putString(KEY_LAST, lastName)
            .putInt(KEY_TEAM_ID, teamId ?: -1)
            .apply()
    }

    fun getToken(): String? = prefs.getString(KEY_TOKEN, null)

    fun getBearerToken(): String = "Bearer ${getToken()}"

    fun getUserId(): Int = prefs.getInt(KEY_USER_ID, -1)

    fun getRole(): String? = prefs.getString(KEY_ROLE, null)

    fun getFirstName(): String = prefs.getString(KEY_FIRST, "") ?: ""

    fun getLastName(): String = prefs.getString(KEY_LAST, "") ?: ""

    fun getTeamId(): Int? {
        val v = prefs.getInt(KEY_TEAM_ID, -1)
        return if (v == -1) null else v
    }

    fun isLoggedIn(): Boolean = getToken() != null

    fun clearSession() = prefs.edit().clear().apply()
}
