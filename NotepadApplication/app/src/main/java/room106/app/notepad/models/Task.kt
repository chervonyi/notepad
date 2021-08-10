package room106.app.notepad.models

class Task(val title: String, var status: Boolean) {

    fun isNotBlank() : Boolean {
        return title.isNotBlank()
    }
}