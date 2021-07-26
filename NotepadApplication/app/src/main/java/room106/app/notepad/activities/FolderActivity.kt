package room106.app.notepad.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import room106.app.notepad.R
import room106.app.notepad.fragments.NotesListFragment

class FolderActivity : AppCompatActivity() {

    private var notesFragment: NotesListFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_folder)

        notesFragment = NotesListFragment()
        notesFragment!!.folderID = intent.getIntExtra("folder_id", -1)
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frameLayout, notesFragment!!)
        transaction.commit()
    }
}