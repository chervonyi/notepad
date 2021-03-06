package room106.app.notepad.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RelativeLayout
import androidx.viewpager.widget.ViewPager
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.tabs.TabLayout
import room106.app.notepad.R
import room106.app.notepad.fragments.NotesListFragment.Companion.PREFERENCE_FILE_KEY
import room106.app.notepad.fragments.NotesListFragment.Companion.VIEW_OPTION_KEY
import room106.app.notepad.fragments.TabPagerAdapter
import room106.app.notepad.models.JSONFileReader
import room106.app.notepad.models.Vault

class MainActivity : AppCompatActivity() {

    // Views
    private lateinit var topAppBar: MaterialToolbar
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager
    private lateinit var emptyListLayout: RelativeLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        topAppBar = findViewById(R.id.topAppBar)
        tabLayout = findViewById(R.id.tabLayout)
        viewPager = findViewById(R.id.viewPager)
        emptyListLayout = findViewById(R.id.emptyListLayout)

        //region Listeners
        viewPager.adapter = TabPagerAdapter(supportFragmentManager, isEmptyNotesList())
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager.currentItem = tab.position
            }
            override fun onTabUnselected(tab: TabLayout.Tab) { }
            override fun onTabReselected(tab: TabLayout.Tab) { }
        })

        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))

        topAppBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_change_passcode -> {
                    val intent = Intent(this, PasscodeActivity::class.java)
                    intent.putExtra("request", PasscodeActivity.CHANGE_PASSCODE_REQUEST)
                    startActivity(intent)
                }

                R.id.menu_switch_view -> {
                    switchNotesViewOption()

                    // Refresh current activity with a new option view mode
                    (viewPager.adapter as TabPagerAdapter).updateNotesListViewOption()
                }
            }
            true
        }
        //endregion
    }

    fun onClickFloatingButton(v: View) {
        if (tabLayout.selectedTabPosition == 0) {
            val intent = Intent(this, NoteActivity::class.java)
            startActivity(intent)
        } else {
            (viewPager.adapter as TabPagerAdapter).createNewFolder()
        }
    }

    override fun onResume() {
        super.onResume()
        updateVaultFromJSON()
        updateViewToNotesList()
    }

    private fun updateViewToNotesList() {
        if (isEmptyNotesList()) {
            emptyListLayout.visibility = View.VISIBLE
            viewPager.visibility = View.INVISIBLE
            tabLayout.visibility = View.INVISIBLE
        } else {
            emptyListLayout.visibility = View.INVISIBLE
            viewPager.visibility = View.VISIBLE
            tabLayout.visibility = View.VISIBLE
        }
    }

    private fun isEmptyNotesList() : Boolean {
        return Vault.instance?.notes?.isEmpty() ?: false
    }

    private fun updateVaultFromJSON() {
//        JSONFileReader().saveVault(requireContext(), "")

        Log.d("Test", "MainActivity - updateVaultFromJSON")
        Vault.instance = JSONFileReader().readVault(this)
        Vault.instance?.checkInitialFolders(this)
    }

    private fun switchNotesViewOption() {
        val sharedPref = getSharedPreferences(PREFERENCE_FILE_KEY, Context.MODE_PRIVATE)
        val currentMode = sharedPref.getBoolean(VIEW_OPTION_KEY, false)
        with (sharedPref.edit()) {
            putBoolean(VIEW_OPTION_KEY, !currentMode)
            apply()
        }
    }
}