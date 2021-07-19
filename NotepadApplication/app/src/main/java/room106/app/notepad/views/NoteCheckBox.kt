package room106.app.notepad.views

import android.content.Context
import android.content.res.Resources
import android.graphics.Paint
import android.text.InputType
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.core.content.ContextCompat
import room106.app.notepad.R
import room106.app.notepad.models.Task

class NoteCheckBox: AppCompatCheckBox {

    constructor(context: Context, task: Task) : super(context) {
        initializeView(context)
        assignData(task)
    }

    constructor(context: Context) : super(context) {
        initializeView(context)
    }
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initializeView(context)
    }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initializeView(context)
    }

    private fun initializeView(context: Context) {
        setPadding(10.dp, 5.dp, 0, 5.dp)
        buttonDrawable = ContextCompat.getDrawable(context, R.drawable.note_checkbox_button)
        setTextColor(context.getColor(R.color.note_block_text_1))
        setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimension(R.dimen.note_block_text_size))
        inputType = InputType.TYPE_TEXT_FLAG_CAP_SENTENCES
        background = null
        gravity = Gravity.TOP
        minimumHeight = 0

        setOnCheckedChangeListener { compoundButton, b ->
            paintFlags = if (b) {
                Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                0
            }
        }
    }

    private fun assignData(task: Task) {
        text = task.title
        isChecked = task.status
    }

    private var _isHighlighted = false
    var isHighlighted: Boolean
        get() = _isHighlighted
        set(value) {
            _isHighlighted = value

            if (value) {
                buttonDrawable = ContextCompat.getDrawable(context,
                    R.drawable.note_checkbox_button_highlighted
                )
                setTextColor(context.getColor(R.color.note_block_highlighted_text_1))
            } else {
                buttonDrawable = ContextCompat.getDrawable(context, R.drawable.note_checkbox_button)
                setTextColor(context.getColor(R.color.note_block_text_1))
            }
        }


    val Int.px: Int
        get() = (this / Resources.getSystem().displayMetrics.density).toInt()

    val Int.dp: Int
        get() = (this * Resources.getSystem().displayMetrics.density).toInt()
}