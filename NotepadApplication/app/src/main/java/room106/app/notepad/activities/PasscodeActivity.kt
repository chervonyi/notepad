package room106.app.notepad.activities

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.core.widget.addTextChangedListener
import com.google.android.material.appbar.MaterialToolbar
import room106.app.notepad.R

class PasscodeActivity : AppCompatActivity() {

    private lateinit var passcodeEditText: AppCompatEditText
    private lateinit var topAppBar: MaterialToolbar
    private lateinit var passcodeItems: LinearLayoutCompat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_passcode)

        passcodeEditText = findViewById(R.id.passcodeEditText)
        topAppBar = findViewById(R.id.topAppBar)
        passcodeItems = findViewById(R.id.passcodeItemsLinearLayout)

        //region Listeners
        topAppBar.setNavigationOnClickListener {
            finish()
        }

        passcodeEditText.addTextChangedListener {
            val passcode = it.toString()
            Toast.makeText(this, passcode, Toast.LENGTH_SHORT).show()
            updateCheckmarks(passcode.length)
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
    }

    private fun showKeyboard() {
        passcodeItems.requestFocus()
        passcodeEditText.requestFocus()
        val imm: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(passcodeEditText, InputMethodManager.SHOW_IMPLICIT)
    }
}