package room106.app.notepad.models

import com.google.gson.annotations.Expose

class Task(@Expose val title: String, @Expose val status: Boolean) {

    fun isNotBlank() : Boolean {
        return title.isNotBlank()
    }

}