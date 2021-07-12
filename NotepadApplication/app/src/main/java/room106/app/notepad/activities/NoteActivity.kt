package room106.app.notepad.activities

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.LinearLayoutCompat
import room106.app.notepad.NoteCheckBox
import room106.app.notepad.R
import room106.app.notepad.models.Note

class NoteActivity : AppCompatActivity() {

    // Views
    private lateinit var title: TextView
    private lateinit var folder: AppCompatButton
    private lateinit var date: TextView
    private lateinit var body: TextView
    private lateinit var tasks: LinearLayoutCompat

    private var note: Note? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)

        // Connect views
        title = findViewById(R.id.note_title)
        folder = findViewById(R.id.note_folder)
        date = findViewById(R.id.date_time)
        body = findViewById(R.id.note_body)
        tasks = findViewById(R.id.note_tasks)


        note = intent.getParcelableExtra<Note>("note")

        if (note != null) {
            updateData(note!!)
            Log.d("Test", "Note: " + note!!.title)
        } else {
            Log.d("Test", "Note is null")
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updateData(note: Note) {
        title.text = note.title
        date.text = note.date + ", " + note.time
        folder.text = note.folder
        body.text = note.body

        tasks.removeAllViews()

        for (task in note.tasks) {
            val taskCheckBox = NoteCheckBox(this, task)
            tasks.addView(taskCheckBox)
        }
    }
}