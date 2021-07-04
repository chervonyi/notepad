package room106.app.notepad

import android.content.Context
import android.content.res.Resources
import android.graphics.Paint
import android.util.AttributeSet
import android.util.TypedValue
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.ContextCompat

class NoteCheckBox: AppCompatCheckBox {

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
        val param = LinearLayoutCompat.LayoutParams(10.dp, 10.dp)
        layoutParams = param

        setPadding(10.dp, 0, 0, 0)
        buttonDrawable = ContextCompat.getDrawable(context, R.drawable.note_checkbox_button)
        setTextColor(context.getColor(R.color.note_block_text_1))
        setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimension(R.dimen.note_block_text_size))
        background = null

        setOnCheckedChangeListener { compoundButton, b ->
            paintFlags = if (b) {
                Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                0
            }
        }
    }






    val Int.px: Int
        get() = (this / Resources.getSystem().displayMetrics.density).toInt()

    val Int.dp: Int
        get() = (this * Resources.getSystem().displayMetrics.density).toInt()
}