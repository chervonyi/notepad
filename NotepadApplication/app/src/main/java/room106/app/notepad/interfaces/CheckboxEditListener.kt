package room106.app.notepad.interfaces

import android.view.View

interface CheckboxEditListener {
    fun createNewCheckbox(view: View)
    fun removeSelectedCheckbox(view: View)
}