package room106.app.notepad.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.core.widget.addTextChangedListener
import com.google.android.material.appbar.MaterialToolbar
import room106.app.notepad.R
import room106.app.notepad.models.LockManager

class PasscodeActivity : AppCompatActivity() {

    private lateinit var titleTextView: TextView
    private lateinit var bodyTextView: TextView
    private lateinit var passcodeEditText: AppCompatEditText
    private lateinit var topAppBar: MaterialToolbar
    private lateinit var passcodeItems: LinearLayoutCompat

    private var request = 0
    private var noteID = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_passcode)

        //region Connect views
        titleTextView = findViewById(R.id.titleTextView)
        bodyTextView = findViewById(R.id.bodyTextView)
        passcodeEditText = findViewById(R.id.passcodeEditText)
        topAppBar = findViewById(R.id.topAppBar)
        passcodeItems = findViewById(R.id.passcodeItemsLinearLayout)
        //endregion

        request = intent.getIntExtra("request", 0)
        noteID = intent.getIntExtra("note_id", 0)

        //region Prepare view
        when(request) {
            CREATE_PASSCODE_REQUEST -> {
                titleTextView.text = getString(R.string.create_passcode_title)
                bodyTextView.visibility = View.VISIBLE
            }
            OPEN_NOTE_REQUEST -> {
                titleTextView.text = getString(R.string.enter_passcode_title)
                bodyTextView.visibility = View.GONE
            }

            CHANGE_PASSCODE_REQUEST -> {
                titleTextView.text = getString(R.string.change_passcode_title)
                bodyTextView.visibility = View.GONE
            }
        }
        //endregion

        //region Attach listeners
        topAppBar.setNavigationOnClickListener {
            finish()
        }

        passcodeEditText.addTextChangedListener {
            val passcode = it.toString()
            updateCheckmarks(passcode.length)
        }

//        passcodeEditText.setOnKeyListener { v, i, keyEvent ->
//            if (keyEvent.action == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_ENTER) {
//                submitPasscode()
//            }
//            false
//        }

        passcodeItems.setOnClickListener {
            showKeyboard()
        }
        //endregion

        // Show keyboard on activity start up
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    private fun updateCheckmarks(size: Int) {
        val item = ContextCompat.getDrawable(this, R.drawable.passcode_item)
        val checkedItem = ContextCompat.getDrawable(this, R.drawable.passcode_item_checked)

        for (i in 1..4) {
            passcodeItems[i - 1].background = if (i <= size) {
                checkedItem
            } else {
                item
            }
        }

        if (size > 3) {
            // Automatically submit passcode when typed the last digit
            submitPasscode()
        }
    }

    private fun submitPasscode() {
        val passcode = passcodeEditText.text.toString()

        when (request) {
            CREATE_PASSCODE_REQUEST -> {
                LockManager(this).setPasscode(passcode)
                finish()
            }

            OPEN_NOTE_REQUEST -> {
                if (LockManager(this).matchPasscode(passcode)) {
                    val intent = Intent(this, NoteActivity::class.java)
                    intent.putExtra("note", noteID)
                    startActivity(intent)
                    finish()
                } else {
                    clearWithDelay()
                }
            }

            CHANGE_PASSCODE_REQUEST -> {
                if (LockManager(this).matchPasscode(passcode)) {
                    val intent = Intent(this, PasscodeActivity::class.java)
                    intent.putExtra("request", CREATE_PASSCODE_REQUEST)
                    startActivity(intent)
                    finish()
                } else {
                    clearWithDelay()
                }
            }
        }
    }

    private fun clearWithDelay() {
        Handler(mainLooper).postDelayed({
            Toast.makeText(this, "Wrong passcode. Try again.", Toast.LENGTH_SHORT).show()
            passcodeEditText.text?.clear()
            showKeyboard()
        }, 300)
    }

    private fun showKeyboard() {
        passcodeItems.requestFocus()
        passcodeEditText.requestFocus()
        val imm: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(passcodeEditText, InputMethodManager.SHOW_IMPLICIT)
    }

    companion object {
        const val CREATE_PASSCODE_REQUEST = 10001
        const val OPEN_NOTE_REQUEST = 10002
        const val CHANGE_PASSCODE_REQUEST = 10003
    }
}