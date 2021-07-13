package room106.app.notepad.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.LinearLayoutCompat
import room106.app.notepad.R
import room106.app.notepad.models.Note
import room106.app.notepad.models.Task
import room106.app.notepad.views.NoteView

class AllNotesTabFragment : Fragment() {

    // Views
    private lateinit var leftColumn: LinearLayoutCompat
    private lateinit var rightColumn: LinearLayoutCompat

    private var nextLeftColumn = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_all_notes_tab, container, false)

        leftColumn = view.findViewById(R.id.leftColumn)
        rightColumn = view.findViewById(R.id.rightColumn)


        val tasks = ArrayList<Task>().apply {
            add(Task("Check emails", false))
            add(Task("Write text", false))
        }

        val notes = ArrayList<Note>().apply {
            add(Note("Note 1", "My first body.", tasks, "Personal", "Oct 10", "14:20", false))
            add(Note("Note 2", "My second body.", tasks, "Important", "Oct 12", "20:10", true))
            add(Note("Note 3", "My third body.", tasks, "Secret", "Oct 13", "16:34", false))
        }

        fillData(notes)

        return view
    }

    private fun fillData(notes: ArrayList<Note>) {
        for (note in notes) {
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