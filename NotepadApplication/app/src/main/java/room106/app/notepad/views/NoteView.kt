package room106.app.notepad.views

import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.ContextCompat
import androidx.core.view.get
import room106.app.notepad.R
import room106.app.notepad.activities.MainActivity
import room106.app.notepad.activities.NoteActivity
import room106.app.notepad.activities.PasscodeActivity
import room106.app.notepad.models.Note
import room106.app.notepad.models.Vault

class NoteView: LinearLayoutCompat {

    //region Views
    private lateinit var titleTextView: TextView
    private lateinit var bodyTextView: TextView
    private lateinit var tasksLinearLayoutCompat: LinearLayoutCompat
    private lateinit var folderTextView: TextView
    private lateinit var dateTextView: TextView
    private lateinit var iconImageView: AppCompatImageView
    //endregion

    private var note: Note? = null

    //region Constructors
    constructor(context: Context, note: Note) : super(context) {
        this.note = note

        if (note.isLocked) {
//            note.isLocked = false
//            initializeView(context)
            initializeLockedNoteView(context)
        } else {
            initializeView(context)
        }

        setData(note)
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
    //endregion

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
                intent.putExtra("note", note!!.id)
                (context as MainActivity).startActivity(intent)
            }
        }
    }

    private fun initializeLockedNoteView(context: Context) {
        View.inflate(context, R.layout.locked_note_layout, this)

        // Connect views
        titleTextView = findViewById(R.id.titleTextView)
        iconImageView = findViewById(R.id.iconImageView)

        setOnClickListener {
            if (note != null) {
                val intent = Intent(context, PasscodeActivity::class.java)
                intent.putExtra("request", PasscodeActivity.OPEN_NOTE_REQUEST)
                intent.putExtra("note_id", note!!.id)
                (context as MainActivity).startActivity(intent)
            }
        }
    }

    private fun setLockedNoteHighlight(isHighlighted: Boolean) {
        background = ContextCompat.getDrawable(context, R.drawable.note_block)
        var text0Color = ContextCompat.getColor(context, R.color.note_block_text_0)
        var iconTint = ContextCompat.getColor(context, R.color.note_block_folder_background)

        if (isHighlighted) {
            background = ContextCompat.getDrawable(context, R.drawable.note_block_highlighted)
            text0Color = ContextCompat.getColor(context, R.color.note_block_highlighted_text_0)
            iconTint = ContextCompat.getColor(context, R.color.note_block_highlighted_text_0)
        }

        titleTextView.setTextColor(text0Color)
        iconImageView.setColorFilter(iconTint)
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

    private fun setData(note: Note) {
        titleTextView.text = note.title

        if (note.isLocked) {
            setLockedNoteHighlight(note.isHighlighted)
            return
        }

        bodyTextView.text = note.body
        folderTextView.text = Vault.instance?.getFolderNameById(note.folder) ?: ""
        dateTextView.text = note.date

        tasksLinearLayoutCompat.removeAllViews()

        for (i in 0 until note.tasks.size) {
            val taskCheckBox = NoteCheckBox(context, note.tasks[i])
            taskCheckBox.setOnCheckedChangeListener { button, b ->
                note.tasks[i].status = b
                button.paintFlags = if (b) {
                    Paint.STRIKE_THRU_TEXT_FLAG
                } else {
                    0
                }

                updateJSON()
            }
            tasksLinearLayoutCompat.addView(taskCheckBox)
        }

        setHighlight(note.isHighlighted)
    }

    private fun updateJSON() {
        if (note != null && note!!.id != 0) {
            Vault.instance?.update(context, note!!)
        }
    }
}