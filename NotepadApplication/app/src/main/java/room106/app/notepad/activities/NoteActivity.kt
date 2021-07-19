package room106.app.notepad.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.LinearLayoutCompat
import com.google.gson.Gson
import room106.app.notepad.R
import room106.app.notepad.interfaces.CheckboxEditListener
import room106.app.notepad.models.Note
import room106.app.notepad.models.Task
import room106.app.notepad.models.Vault
import room106.app.notepad.views.NoteCheckBox
import room106.app.notepad.views.NoteCheckBoxEditable
import java.text.SimpleDateFormat
import java.util.*

class NoteActivity : AppCompatActivity(), CheckboxEditListener {

    // Views
    private lateinit var title: TextView
    private lateinit var folder: AppCompatButton
    private lateinit var date: TextView
    private lateinit var body: TextView
    private lateinit var tasks: LinearLayoutCompat

    private var note: Note? = null

    // Data
    private var creationDate : Date? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)

        // Connect views
        title = findViewById(R.id.note_title)
        folder = findViewById(R.id.note_folder)
        date = findViewById(R.id.date_time)
        body = findViewById(R.id.note_body)
        tasks = findViewById(R.id.note_tasks)


        note = intent.getParcelableExtra("note")

        if (note != null) {
            updateData(note!!)
            Log.d("Test", "Note: " + note!!.title)
        } else {
            prepareNewNote()
            Log.d("Test", "Note is null")
        }
    }


    @SuppressLint("SimpleDateFormat")
    private fun prepareNewNote() {
        // Set current date and time as a 'creation date'
        creationDate = Calendar.getInstance().time
        val format = SimpleDateFormat("MMM d, HH:mm")
        date.text = format.format(creationDate?.time)

        val emptyCheckbox = NoteCheckBoxEditable(this, this)
        tasks.addView(emptyCheckbox)
    }


    @SuppressLint("SimpleDateFormat")
    private fun analyzeEnteredData() {

        if (note == null) {
            note = Note()

            val dateFormat = SimpleDateFormat("MMM d")
            val timeFormat = SimpleDateFormat("HH:mm")
            note!!.date = dateFormat.format(creationDate?.time)
            note!!.time = timeFormat.format(creationDate?.time)

            note!!.folder = "" // NO-FOLDER
        }

        note!!.title = title.text.toString()
        note!!.body = body.text.toString()

        note!!.tasks.clear()
        for (i in 0 until tasks.childCount) {
            val taskView = tasks.getChildAt(i) as NoteCheckBoxEditable
            val task = Task(taskView.getTaskTitle(), taskView.getTaskStatus())

            if (task.isNotBlank()) {
                note!!.tasks.add(task)
            }
        }

        if (note!!.id == 0 && note!!.isNotBlank()) {
            note!!.assignUniqueId(this)
        }

        if (note!!.id != 0) {
            Vault.instance?.update(this, note!!)
        }

//        Log.d("Test", "Updated note: " + Gson().toJson(note))
    }


    @SuppressLint("SetTextI18n")
    private fun updateData(note: Note) {
        title.text = note.title
        date.text = note.date + ", " + note.time
        folder.text = note.folder
        body.text = note.body

        tasks.removeAllViews()

        for (task in note.tasks) {
            val taskCheckBox = NoteCheckBoxEditable(this, task)
            tasks.addView(taskCheckBox)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        analyzeEnteredData()
    }

    //region Task Checkbox Methods
    override fun createNewCheckbox(view: View) {
        val nextCheckBoxIndex = tasks.indexOfChild(view) + 1
        val newCheckbox = NoteCheckBoxEditable(this, this)
        tasks.addView(newCheckbox, nextCheckBoxIndex)
        newCheckbox.requestFocus2()
    }

    override fun removeSelectedCheckbox(view: View) {
        TODO("Not yet implemented")
    }
    //endregion
}