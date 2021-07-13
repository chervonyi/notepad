package room106.app.notepad.views

import android.content.Context
import android.content.Intent
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.ContextCompat
import androidx.core.view.get
import room106.app.notepad.R
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

    private fun setHighlight(isHighlighted: Boolean) {

        background = ContextCompat.getDrawable(context, R.drawable.note_block)
        var text0Color = ContextCompat.getColor(context, R.color.note_block_text_0)
        var text1Color = ContextCompat.getColor(context, R.color.note_block_text_1)
        var folderBackground = ContextCompat.getDrawable(context, R.drawable.note_block_folder)
        var folderTextColor = ContextCompat.getColor(context, R.color.note_block_folder_text)

        if (isHighlighted) {
            background = ContextCompat.getDrawable(context, R.drawable.note_block_highlighted)
            text0Color = ContextCompat.getColor(context, R.color.note_block_highlighted_text_0)
            text1Color = ContextCompat.getColor(context, R.color.note_block_highlighted_text_1)
            folderBackground = ContextCompat.getDrawable(context,
                R.drawable.note_block_highlighted_folder
            )
            folderTextColor = ContextCompat.getColor(context,
                R.color.note_block_highlighted_folder_text
            )
        }

        titleTextView.setTextColor(text0Color)
        bodyTextView.setTextColor(text1Color)
        dateTextView.setTextColor(text1Color)
        folderTextView.background = folderBackground
        folderTextView.setTextColor(folderTextColor)

        for (i in 0 until tasksLinearLayoutCompat.childCount) {
            (tasksLinearLayoutCompat[i] as NoteCheckBox).isHighlighted = isHighlighted
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

        setHighlight(note.isHighlighted)
    }


}