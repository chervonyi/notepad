package room106.app.notepad.models

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import room106.app.notepad.R

class Note {

    @Expose var id = 0
        private set
    @Expose var title = ""
    @Expose var body = ""
    @Expose var tasks = ArrayList<Task>()
    @Expose var folder = -1
    @Expose var date = ""
    @Expose var time = ""
    @Expose var isHighlighted = false
    @Expose var isLocked = false


    private val PREFERENCE_FILE_KEY = "PREFERENCE_FILE_KEY"

    private val NOTE_UNIQUE_ID_KEY = "NOTE_UNIQUE_ID_KEY"


    fun isNotBlank() : Boolean {
        return title.isNotBlank() || body.isNotBlank() || tasks.isNotEmpty()
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
}