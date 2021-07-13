package room106.app.notepad.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.LinearLayoutCompat
import room106.app.notepad.R
import room106.app.notepad.models.Folder
import room106.app.notepad.views.FolderView

class FoldersFragment : Fragment() {

    // Views
    private lateinit var leftColumn: LinearLayoutCompat
    private lateinit var rightColumn: LinearLayoutCompat

    private var nextLeftColumn = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_folders, container, false)

        leftColumn = view.findViewById(R.id.leftColumn)
        rightColumn = view.findViewById(R.id.rightColumn)

        val folders = ArrayList<Folder>().apply {
            add(Folder("Important", 2, false))
            add(Folder("Personal", 4, true))
            add(Folder("Work", 8, false))
        }

        fillData(folders)

        return view
    }

    private fun fillData(folders: ArrayList<Folder>) {
        for (folder in folders) {
            val folderView = FolderView(requireContext(), folder)

            if (nextLeftColumn) {
                leftColumn.addView(folderView)
            } else {
                rightColumn.addView(folderView)
            }

            nextLeftColumn = !nextLeftColumn
        }
    }
}