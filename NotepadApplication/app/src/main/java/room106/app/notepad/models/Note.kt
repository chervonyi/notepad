package room106.app.notepad.models

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import room106.app.notepad.R

class Note : Parcelable {


    @Expose var id = 0
        private set
    @Expose var title = ""
    @Expose var body = ""
    @Expose var tasks = ArrayList<Task>()
    @Expose var folder = ""
    @Expose var date = ""
    @Expose var time = ""
    @Expose var isHighlighted = false

    constructor() {}

    private val PREFERENCE_FILE_KEY = "PREFERENCE_FILE_KEY"

    private val NOTE_UNIQUE_ID_KEY = "NOTE_UNIQUE_ID_KEY"

    constructor(parcel: Parcel) {
        id = parcel.readInt()
        title = parcel.readString()!!
        body = parcel.readString()!!

        val size = parcel.readInt()
        for (i in 0 until size) {
            val title = parcel.readString()!!
            val done = parcel.readByte() == 1.toByte()
            tasks.add(Task(title, done))
        }

        folder = parcel.readString()!!
        date = parcel.readString()!!
        time = parcel.readString()!!
        isHighlighted = parcel.readInt() == 1
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(p0: Parcel?, p1: Int) {
        p0?.writeInt(id)
        p0?.writeString(title)
        p0?.writeString(body)

        p0?.writeInt(tasks.size)

        for (task in tasks) {
            p0?.writeString(task.title)
            p0?.writeByte((if (task.status) 1 else 0).toByte())
        }

        p0?.writeString(folder)
        p0?.writeString(date)
        p0?.writeString(time)
        p0?.writeInt(if (isHighlighted) 1 else 0)
    }

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

    companion object CREATOR : Parcelable.Creator<Note> {
        override fun createFromParcel(parcel: Parcel): Note {
            return Note(parcel)
        }

        override fun newArray(size: Int): Array<Note?> {
            return arrayOfNulls(size)
        }
    }
}