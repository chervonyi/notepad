package room106.app.notepad.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.core.widget.addTextChangedListener
import com.google.android.material.appbar.MaterialToolbar
import room106.app.notepad.R
import room106.app.notepad.models.LockManager

class PasscodeActivity : AppCompatActivity() {

    private lateinit var passcodeEditText: AppCompatEditText
    private lateinit var topAppBar: MaterialToolbar
    private lateinit var passcodeItems: LinearLayoutCompat
    private lateinit var submitButton: AppCompatButton

    private var request = 0
    private var noteID = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_passcode)

        passcodeEditText = findViewById(R.id.passcodeEditText)
        topAppBar = findViewById(R.id.topAppBar)
        passcodeItems = findViewById(R.id.passcodeItemsLinearLayout)
        submitButton = findViewById(R.id.submitButton)

        request = intent.getIntExtra("request", 0)
        noteID = intent.getIntExtra("note_id", 0)

        when(request) {
            CREATE_PASSCODE_REQUEST -> {
                // TODO - Prepare title "Create a new passcode"
            }
            OPEN_NOTE_REQUEST -> {
                // TODO - Prepare title "Enter passcode"
            }
        }

        //region Listeners
        topAppBar.setNavigationOnClickListener {
            finish()
        }

        passcodeEditText.addTextChangedListener {
            val passcode = it.toString()
            updateCheckmarks(passcode.length)
        }

        passcodeEditText.setOnKeyListener { v, i, keyEvent ->
            if (keyEvent.action == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_ENTER) {
                onClickSubmit(v)
            }
            false
        }

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

        submitButton.visibility = if (size > 3) {
            View.VISIBLE
        } else {
            View.INVISIBLE
        }
    }

    fun onClickSubmit(v: View) {
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
                    Toast.makeText(this, "Wrong passcode. Try again.", Toast.LENGTH_SHORT).show()
                    passcodeEditText.text?.clear()
                    showKeyboard()
                }
            }
        }
    }

    private fun showKeyboard() {
        passcodeItems.requestFocus()
        passcodeEditText.requestFocus()
        val imm: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(passcodeEditText, InputMethodManager.SHOW_IMPLICIT)
    }

    companion object {
        val CREATE_PASSCODE_REQUEST = 10001
        val OPEN_NOTE_REQUEST = 10002
    }
}