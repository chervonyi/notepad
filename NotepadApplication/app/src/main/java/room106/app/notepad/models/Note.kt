package room106.app.notepad.models

import android.os.Parcel
import android.os.Parcelable

class Note : Parcelable {

    var title = ""
        private set
    var body = ""
        private set
    var tasks = ArrayList<Task>()
        private set
    var folder = ""
        private set
    var date = ""
        private set
    var time = ""
        private set
    var isHighlighted = false
        private set

    constructor(parcel: Parcel) {

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
        isHighlighted = parcel.readByte() == 0.toByte()
    }

    constructor(title: String,
                body: String,
                tasks: ArrayList<Task>,
                folder: String,
                date: String,
                time: String,
                isHighlighted: Boolean) {

        this.title = title
        this.body = body
        this.tasks = tasks
        this.folder = folder
        this.date = date
        this.time = time
        this.isHighlighted = isHighlighted
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(p0: Parcel?, p1: Int) {
        p0?.writeString(title)
        p0?.writeString(body)

        p0?.writeInt(tasks.size)

        for (task in tasks) {
            p0?.writeString(task.title)
            p0?.writeByte((if (task.done) 1 else 0).toByte())
        }

        p0?.writeString(folder)
        p0?.writeString(date)
        p0?.writeString(time)
        p0?.writeByte((if (isHighlighted) 1 else 0).toByte())
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