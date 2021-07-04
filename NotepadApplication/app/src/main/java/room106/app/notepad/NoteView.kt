package room106.app.notepad

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.LinearLayoutCompat

class NoteView: LinearLayoutCompat {

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
        View.inflate(context, R.layout.note_layout, this)


//        yourTextView.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);    FOR CROSSED OUT TEXT FOR CHECKED CHECKBOX!
    }
}