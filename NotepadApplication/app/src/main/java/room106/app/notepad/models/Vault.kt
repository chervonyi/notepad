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

    @Expose
    var folders = HashMap<Int, Folder>()
        private set

    fun update(context: Context, note: Note) {
        notes[note.id] = note
        updateJSONFile(context)
    }

    private fun updateJSONFile(context: Context) {
        jsonFileReader.saveVault(context, JSON)
        Log.d("Test", "UPDATE JSON: $JSON")
    }

    fun checkInitialFolders(context: Context) {
        if (Folder.getNextUniqueIDKey(context) == 100) {
            Log.d("Test", "Creating the initials folders!")
            // Should create initials folders
            val folder1 = Folder("Important", false).apply {
                assignUniqueId(context)
            }
            val folder2 = Folder("Work", false).apply {
                assignUniqueId(context)
            }

            folders[folder1.id] = folder1
            folders[folder2.id] = folder2

            updateJSONFile(context)
        } else {
            Log.d("Test", "Initials folders had been already created!")
        }
    }

    fun getFolderArray(): ArrayList<String> {
        val array = ArrayList<String>()
        array.add("No folder")
        for ((id, folder) in folders) {
            array.add(folder.title)
        }
        return array
    }

    fun getNotesByFolder(id: Int): Map<Int, Note> {
        val folderName = folders[id]?.title ?: HashMap<Int, Note>()
        return notes.filter {
            it.value.folder == folderName
        }
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