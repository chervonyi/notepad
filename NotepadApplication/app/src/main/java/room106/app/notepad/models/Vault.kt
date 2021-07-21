package room106.app.notepad.models

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.annotations.Expose

class Vault {

    private val jsonFileReader = JSONFileReader()

    @Expose
    var notes = HashMap<Int, Note>()
        private set

    fun update(context: Context, note: Note) {
        notes[note.id] = note
        updateJSONFile(context)
    }

    private fun updateJSONFile(context: Context) {
        jsonFileReader.saveVault(context, JSON)
        Log.d("Test", "UPDATE JSON: $JSON")
    }

    var JSON: String
        private set(value) {}
        get() {
            val gson = GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()
            return gson.toJson(this)
        }

    companion object {
        var instance: Vault? = null
    }
}