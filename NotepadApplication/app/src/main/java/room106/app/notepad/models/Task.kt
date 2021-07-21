package room106.app.notepad.models

import com.google.gson.annotations.Expose

class Task(@Expose val title: String, @Expose var status: Boolean) {

    fun isNotBlank() : Boolean {
        return title.isNotBlank()
    }

}