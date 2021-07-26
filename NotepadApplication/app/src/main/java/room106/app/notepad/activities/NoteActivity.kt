package room106.app.notepad.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.LinearLayoutCompat
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import room106.app.notepad.R
import room106.app.notepad.interfaces.CheckboxEditListener
import room106.app.notepad.models.Note
import room106.app.notepad.models.Task
import room106.app.notepad.models.Vault
import room106.app.notepad.views.NoteCheckBoxEditable
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class NoteActivity : AppCompatActivity(), CheckboxEditListener {

    //region Views
    private lateinit var topAppBar: MaterialToolbar
    private lateinit var title: TextView
    private lateinit var folder: AppCompatButton
    private lateinit var date: TextView
    private lateinit var body: TextView
    private lateinit var tasks: LinearLayoutCompat
    //endregion

    // Data
    private var note: Note? = null
    private var creationDate : Date? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)

        //region Connect views
        topAppBar = findViewById(R.id.topAppBar)
        title = findViewById(R.id.note_title)
        folder = findViewById(R.id.note_folder)
        date = findViewById(R.id.date_time)
        body = findViewById(R.id.note_body)
        tasks = findViewById(R.id.note_tasks)
        //endregion

        //region Listeners
        topAppBar.setNavigationOnClickListener {
            updateModelOnFinish()
            finish()
        }

        topAppBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_highlight -> {

                    note!!.isHighlighted = !note!!.isHighlighted
                    if (note!!.isHighlighted) {
                        it.title = getString(R.string.menu_unhighlight)
                    } else {
                        it.title = getString(R.string.menu_highlight)
                    }
                }
            }
            true
        }

        //endregion

        note = intent.getParcelableExtra("note")

        if (note != null) {
            updateView(note!!)
        } else {
            prepareNoteInstance()
        }

        updateMenuItems()
    }



    @SuppressLint("SimpleDateFormat")
    private fun prepareNoteInstance() {
        // Set current date and time as a 'creation date'
        creationDate = Calendar.getInstance().time
        val format = SimpleDateFormat("MMM d, HH:mm")
        date.text = format.format(creationDate?.time)

        val emptyCheckbox = NoteCheckBoxEditable(this, this)
        tasks.addView(emptyCheckbox)

        note = Note()

        val dateFormat = SimpleDateFormat("MMM d")
        val timeFormat = SimpleDateFormat("HH:mm")
        note!!.date = dateFormat.format(creationDate?.time)
        note!!.time = timeFormat.format(creationDate?.time)

        note!!.folder = "" // NO-FOLDER
    }

    @SuppressLint("SimpleDateFormat")
    private fun updateModelOnFinish() {
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
            Log.d("Test", "Updating note in Vault")
            Vault.instance?.update(this, note!!)
        }
    }


    @SuppressLint("SetTextI18n")
    private fun updateView(note: Note) {
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

    fun onClickSelectFolder(v: View) {
        note ?: return

        val allFolders = Vault.instance?.getFolderArray() ?: ArrayList()

        var checkedItem = 0
        allFolders.indexOf(note!!.folder).also {
            if (it != -1) {
                checkedItem = it
            }
        }

        Log.d("Test", "AllFolders: ${allFolders}")

        MaterialAlertDialogBuilder(this)
            .setTitle("Select folder")
            .setNeutralButton(resources.getString(R.string.cancel)) { _, _ -> }
            .setPositiveButton(resources.getString(R.string.ok)) { dialog, _ ->
                val lw: ListView = (dialog as AlertDialog).listView

                if (lw.checkedItemPosition == 0) {
                    note!!.folder = ""
                    (v as AppCompatButton).text = note!!.folder
                    // Set no-folder icon
                } else {
                    note!!.folder = allFolders[lw.checkedItemPosition]
                    (v as AppCompatButton).text = note!!.folder
                }
            }
            .setSingleChoiceItems(allFolders.toTypedArray(), checkedItem) { _, _ -> }
            .show()
    }

    //region Other
    private fun updateMenuItems() {
        val it = topAppBar.menu.findItem(R.id.menu_highlight)
        if (note!!.isHighlighted) {
            it.title = getString(R.string.menu_unhighlight)
        } else {
            it.title = getString(R.string.menu_highlight)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        updateModelOnFinish()
    }
    //endregion

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