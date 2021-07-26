package room106.app.notepad.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.LinearLayoutCompat
import room106.app.notepad.R
import room106.app.notepad.models.Folder
import room106.app.notepad.models.JSONFileReader
import room106.app.notepad.models.Vault
import room106.app.notepad.views.FolderView

class FoldersListFragment : Fragment() {

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

        return view
    }

    override fun onResume() {
        super.onResume()
        updateView(Vault.instance!!.folders)
    }

    private fun updateView(folders: HashMap<Int, Folder>) {
        leftColumn.removeAllViews()
        rightColumn.removeAllViews()
        nextLeftColumn = true

        for ((id, folder) in folders) {
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