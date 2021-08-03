package room106.app.notepad.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.LinearLayoutCompat
import room106.app.notepad.R
import room106.app.notepad.models.JSONFileReader
import room106.app.notepad.models.Note
import room106.app.notepad.models.Vault
import room106.app.notepad.views.NoteView

class NotesListFragment : Fragment() {

    // Views
    private lateinit var singleColumn: LinearLayoutCompat
    private lateinit var leftColumn: LinearLayoutCompat
    private lateinit var rightColumn: LinearLayoutCompat

    private var nextLeftColumn = true

    var folderID = -1
    var isSingleColumn = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_all_notes_tab, container, false)

        singleColumn = view.findViewById(R.id.singleColumn)
        leftColumn = view.findViewById(R.id.leftColumn)
        rightColumn = view.findViewById(R.id.rightColumn)

        isSingleColumn = isSingleColumn(requireContext())

        return view
    }

    override fun onResume() {
        super.onResume()
        Log.d("Test", "Update NotesFragment with $folderID id")
        if (folderID == -1) {
            // Show ALL notes
            updateView(Vault.instance!!.notes)
        } else {
            // Show notes for selected folder
            updateView(Vault.instance!!.getNotesByFolder(folderID))
        }
    }

    private fun updateView(notes: Map<Int, Note>?) {
        // Clear all lists before filling up with a new data
        leftColumn.removeAllViews()
        rightColumn.removeAllViews()
        singleColumn.removeAllViews()

        if (isSingleColumn) {
            updateViewSingleList(notes)
        } else {
            updateViewTwoLists(notes)
        }
    }

    private fun updateViewSingleList(notes: Map<Int, Note>?) {
        notes ?: return

        for ((_, note) in notes) {
            val noteView = NoteView(requireContext(), note)
            singleColumn.addView(noteView)
        }
    }

    private fun updateViewTwoLists(notes: Map<Int, Note>?) {
        nextLeftColumn = true

        notes ?: return

        Log.d("Test", "FillData. Notes.size: " + notes.size)
        for ((_, note) in notes) {
            val noteView = NoteView(requireContext(), note)

            if (nextLeftColumn) {
                leftColumn.addView(noteView)
            } else {
                rightColumn.addView(noteView)
            }

            nextLeftColumn = !nextLeftColumn
        }
    }

    private fun isSingleColumn(context: Context) : Boolean {
        val sharedPref = context.getSharedPreferences(PREFERENCE_FILE_KEY, Context.MODE_PRIVATE)
        return sharedPref.getBoolean(VIEW_OPTION_KEY, false)
    }

    fun switchViewOption() {
        isSingleColumn = !isSingleColumn
        updateView(Vault.instance!!.notes)
    }

    companion object {
        const val PREFERENCE_FILE_KEY = "PREFERENCE_FILE_KEY"
        const val VIEW_OPTION_KEY = "VIEW_OPTION_KEY"
    }
}