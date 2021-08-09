package room106.app.notepad.fragments

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import room106.app.notepad.models.Vault

class TabPagerAdapter(fm: FragmentManager, val isListEmpty: Boolean) : FragmentPagerAdapter(fm) {

    private var notesListFragment = NotesListFragment()
    private var folderListFragment = FoldersListFragment()

    override fun getCount(): Int {
        return 2
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> notesListFragment
            1 -> folderListFragment
            else -> Fragment()
        }
    }


    fun createNewFolder() {
        folderListFragment.createNewFolder()
    }

    fun updateNotesListViewOption() {
        notesListFragment.switchViewOption()
    }
}