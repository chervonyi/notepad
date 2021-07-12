package room106.app.notepad

import android.content.Context
import android.content.Intent
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import room106.app.notepad.activities.MainActivity
import room106.app.notepad.activities.NoteActivity
import room106.app.notepad.models.Note

class NoteView: LinearLayoutCompat {

    // Views
    private lateinit var titleTextView: TextView
    private lateinit var bodyTextView: TextView
    private lateinit var tasksLinearLayoutCompat: LinearLayoutCompat
    private lateinit var folderTextView: TextView
    private lateinit var dateTextView: TextView

    private var note: Note? = null

    constructor(context: Context, note: Note) : super(context) {
        this.note = note
        initializeView(context)
        assignData(note)
    }

    constructor(context: Context) : super(context) {
        initializeView(context)
    }
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initializeView(context)
    }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initializeView(context)
    }

    private fun initializeView(context: Context) {
        View.inflate(context, R.layout.note_layout, this)

        // Connect views
        titleTextView = findViewById(R.id.titleTextView)
        bodyTextView = findViewById(R.id.bodyTextView)
        tasksLinearLayoutCompat = findViewById(R.id.tasksLinearLayoutCompat)
        folderTextView = findViewById(R.id.folderTextView)
        dateTextView = findViewById(R.id.dateTextView)

        setOnClickListener {
            if (note != null) {
                val intent = Intent(context, NoteActivity::class.java)
                intent.putExtra("note", note)
                (context as MainActivity).startActivity(intent)
            }
        }
    }

    private fun assignData(note: Note) {
        titleTextView.text = note.title
        bodyTextView.text = note.body
        folderTextView.text = note.folder
        dateTextView.text = note.date

        tasksLinearLayoutCompat.removeAllViews()

        for (task in note.tasks) {
            val taskCheckBox = NoteCheckBox(context, task)
            tasksLinearLayoutCompat.addView(taskCheckBox)
        }
    }


}