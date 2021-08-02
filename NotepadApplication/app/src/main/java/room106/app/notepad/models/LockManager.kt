package room106.app.notepad.models

import android.content.Context

class LockManager(context: Context) {

    private val PREFERENCE_FILE_KEY = "PREFERENCE_FILE_KEY"
    private val PASSCODE_KEY = "PASSCODE_KEY"

    private val sharedPref = context.getSharedPreferences(PREFERENCE_FILE_KEY, Context.MODE_PRIVATE)

    fun isPasscodeReady() : Boolean {
        return getPasscode() != null
    }

    fun matchPasscode(passcodeToCheck: String) : Boolean {
        val realPass = sharedPref.getString(PASSCODE_KEY, null) ?: return false
        return realPass == passcodeToCheck
    }

    private fun getPasscode(): String? {
        return sharedPref.getString(PASSCODE_KEY, null)
    }

    fun setPasscode(newPasscode: String) {
        with(sharedPref.edit()) {
            putString(PASSCODE_KEY, newPasscode)
            apply()
        }
    }
}