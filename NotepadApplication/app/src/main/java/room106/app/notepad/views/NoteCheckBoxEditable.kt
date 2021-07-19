package room106.app.notepad.views

import android.content.Context
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import room106.app.notepad.R
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText
import room106.app.notepad.interfaces.CheckboxEditListener
import room106.app.notepad.models.Task

class NoteCheckBoxEditable: RelativeLayout {

    private lateinit var editText: AppCompatEditText
    private lateinit var checkBox: NoteCheckBox

    private var checkBoxEditListener: CheckboxEditListener? = null

    constructor(context: Context, task: Task) : super(context) {
        initializeView(context)
        assignData(task)
    }

    constructor(context: Context?, checkboxEditListener: CheckboxEditListener) : super(context){
        initializeView(context)
        this.checkBoxEditListener = checkboxEditListener
    }

    constructor(context: Context?) : super(context) {
        initializeView(context)
    }
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        initializeView(context)
    }
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initializeView(context)
    }

    private fun initializeView(context: Context?) {
        View.inflate(context, R.layout.checkbox_layout, this)

        editText = findViewById(R.id.editText)
        checkBox = findViewById(R.id.checkBox)

        editText.imeOptions = EditorInfo.IME_ACTION_DONE
        editText.setRawInputType(InputType.TYPE_CLASS_TEXT)
        editText.inputType = InputType.TYPE_TEXT_FLAG_CAP_SENTENCES

        editText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                if (editText.text != null && editText.text!!.isNotEmpty()) {
                    checkBoxEditListener?.createNewCheckbox(this)
                }
            }
            false
        }

        // TODO - Implement removing current checkbox on pressing Delete (if it's empty)
    }

    fun getTaskTitle() : String {
        return editText.text.toString()
    }

    fun getTaskStatus() : Boolean {
        return checkBox.isChecked
    }

    fun requestFocus2() {
        editText.requestFocus()
    }

    private fun assignData(task: Task) {
        editText.setText(task.title)
        checkBox.isChecked = task.status
    }
}