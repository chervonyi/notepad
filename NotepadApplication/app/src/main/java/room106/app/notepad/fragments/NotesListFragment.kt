package room106.app.notepad.fragments

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
    private lateinit var leftColumn: LinearLayoutCompat
    private lateinit var rightColumn: LinearLayoutCompat

    private var nextLeftColumn = true

    var folderID = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_all_notes_tab, container, false)

        leftColumn = view.findViewById(R.id.leftColumn)
        rightColumn = view.findViewById(R.id.rightColumn)

        return view
    }

    override fun onResume() {
        super.onResume()
        Log.d("Test", "Update NotesFragment with $folderID id")
        if (folderID == -1) {
            updateView(Vault.instance!!.notes)
        } else {
            updateView(Vault.instance!!.getNotesByFolder(folderID))
        }
    }

    private fun updateView(notes: Map<Int, Note>?) {
        leftColumn.removeAllViews()
        rightColumn.removeAllViews()
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
}