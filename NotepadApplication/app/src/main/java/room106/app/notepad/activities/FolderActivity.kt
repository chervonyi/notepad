package room106.app.notepad.activities

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import com.google.android.material.appbar.MaterialToolbar
import room106.app.notepad.R
import room106.app.notepad.fragments.NotesListFragment
import room106.app.notepad.models.Folder
import room106.app.notepad.models.Vault


class FolderActivity : AppCompatActivity() {

    private var notesFragment: NotesListFragment? = null

    private lateinit var topAppBar: MaterialToolbar

    private var folder: Folder? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_folder)

        // Connect views
        topAppBar = findViewById(R.id.topAppBar)

        notesFragment = NotesListFragment()

        // Load folder model
        val id = intent.getIntExtra("folder_id", -1)
        folder = Vault.instance?.folders?.get(id)

        // Locate NotesListFragment into FrameLayout
        notesFragment!!.folderID = id
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frameLayout, notesFragment!!)
        transaction.commit()

        //region Listeners
        topAppBar.setNavigationOnClickListener {
            // TODO - Update this folder properties in JSON
            updateModelOnFinish()
            finish()
        }

        if (folder != null) {
            topAppBar.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.menu_highlight -> {
                        folder!!.isHighlighted = !folder!!.isHighlighted
                        if (folder!!.isHighlighted) {
                            it.title = getString(R.string.menu_unhighlight)
                        } else {
                            it.title = getString(R.string.menu_highlight)
                        }
                    }

                    R.id.menu_rename -> {
                        val builder = AlertDialog.Builder(this)
                        builder.setTitle("Title")

                        val inflatedView = LayoutInflater.from(this).inflate(R.layout.folder_input_layout, findViewById(android.R.id.content), false)
                        val editTextView = inflatedView.findViewById<AppCompatEditText>(R.id.input)
                        builder.setView(inflatedView)

                        builder.setPositiveButton("OK") { _, _ ->
                            folder?.title = editTextView.text.toString()
                            topAppBar.title = editTextView.text.toString()
                        }
                        builder.setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }

                        builder.show()
                    }

                    R.id.menu_delete -> {
                        // TODO - Do something
                    }
                }
                true
            }
        }

        //endregion

        updateAppBarTitle(folder?.title)
        updateMenuItems()
    }

    private fun updateAppBarTitle(folderName : String?) {
        topAppBar.title = folderName ?: ""
    }

    private fun updateModelOnFinish() {
        folder ?: return
        Vault.instance?.update(this, folder!!)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        updateModelOnFinish()
    }

    private fun updateMenuItems() {
        val it = topAppBar.menu.findItem(R.id.menu_highlight)
        if (folder!!.isHighlighted) {
            it.title = getString(R.string.menu_unhighlight)
        } else {
            it.title = getString(R.string.menu_highlight)
        }
    }


}