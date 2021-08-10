package room106.app.notepad.models

import android.content.Context

class Note {

    var id = 0
        private set
    var title = ""
    var body = ""
    var tasks = ArrayList<Task>()
    var folder = -1
    var date = ""
    var time = ""
    var isHighlighted = false
    var isLocked = false

    /**
     * Check if note is able to be saved:
     * - Title mustn't be blank
     * - Body mustn't be blank too OR tasks list mustn't be empty
     */
    fun isNotBlank() : Boolean {
        return title.isNotBlank() && (body.isNotBlank() || tasks.isNotEmpty())
    }

    fun assignUniqueId(context: Context) : Int {
        val sharedPref = context.getSharedPreferences(PREFERENCE_FILE_KEY, Context.MODE_PRIVATE)
        id = sharedPref.getInt(NOTE_UNIQUE_ID_KEY, 100)

        with(sharedPref.edit()) {
            putInt(NOTE_UNIQUE_ID_KEY, id + 1)
            apply()
        }

        return id
    }

    companion object {
        const val PREFERENCE_FILE_KEY = "PREFERENCE_FILE_KEY"
        const val NOTE_UNIQUE_ID_KEY = "NOTE_UNIQUE_ID_KEY"
    }
}