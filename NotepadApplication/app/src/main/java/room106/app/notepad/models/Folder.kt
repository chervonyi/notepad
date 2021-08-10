package room106.app.notepad.models

import android.content.Context
import com.google.gson.annotations.Expose

class Folder {

    @Expose var id = 0
        private set
    @Expose var title = ""
    @Expose var isHighlighted = false

    constructor(title: String) {
        this.title = title
        isHighlighted = false
    }

    constructor(title: String, isHighlighted: Boolean) {
        this.title = title
        this.isHighlighted = isHighlighted
    }

    fun assignUniqueId(context: Context) : Int {
        val sharedPref = context.getSharedPreferences(PREFERENCE_FILE_KEY, Context.MODE_PRIVATE)
        id = sharedPref.getInt(FOLDER_UNIQUE_ID_KEY, 100)

        with(sharedPref.edit()) {
            putInt(FOLDER_UNIQUE_ID_KEY, id + 1)
            apply()
        }

        return id
    }

    var count: Int
        private set(value) {}
        get () {
            return Vault.instance?.getNotesByFolder(id)?.size ?: 0

        }

    companion object {
        const val PREFERENCE_FILE_KEY = "PREFERENCE_FILE_KEY"
        const val FOLDER_UNIQUE_ID_KEY = "FOLDER_UNIQUE_ID_KEY"

        fun getNextUniqueIDKey(context: Context) : Int {
            val sharedPref = context.getSharedPreferences(PREFERENCE_FILE_KEY, Context.MODE_PRIVATE)
            return sharedPref.getInt(FOLDER_UNIQUE_ID_KEY, 100)
        }
    }
}





