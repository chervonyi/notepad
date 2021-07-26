package room106.app.notepad.views

import android.content.Context
import android.content.Intent
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.ContextCompat
import androidx.core.view.get
import room106.app.notepad.R
import room106.app.notepad.activities.FolderActivity
import room106.app.notepad.activities.MainActivity
import room106.app.notepad.models.Folder

class FolderView: LinearLayoutCompat {

    // Views
    private lateinit var titleTextView: TextView
    private lateinit var countTextView: TextView
    private lateinit var iconImageView: AppCompatImageView

    private var folder: Folder? = null

    constructor(context: Context, folder: Folder) : super(context) {
        this.folder = folder
        initializeView(context)
        assignData(folder)
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
        View.inflate(context, R.layout.folder_layout, this)

        // Connect views
        titleTextView = findViewById(R.id.folderTitle)
        countTextView = findViewById(R.id.folderCount)
        iconImageView = findViewById(R.id.folderImageView)

        setOnClickListener {
            if (folder != null) {
                val intent = Intent(context, FolderActivity::class.java)
                intent.putExtra("folder_id", folder!!.id)
                (context as MainActivity).startActivity(intent)
            }
        }
    }

    private fun setHighlight(isHighlighted: Boolean) {

        background = ContextCompat.getDrawable(context, R.drawable.note_block)
        var text0Color = ContextCompat.getColor(context, R.color.note_block_text_0)
        var text1Color = ContextCompat.getColor(context, R.color.note_block_text_1)
        var iconTint = ContextCompat.getColor(context, R.color.note_block_folder_background)


        if (isHighlighted) {
            background = ContextCompat.getDrawable(context, R.drawable.note_block_highlighted)
            text0Color = ContextCompat.getColor(context, R.color.note_block_highlighted_text_0)
            text1Color = ContextCompat.getColor(context, R.color.note_block_highlighted_text_1)
            iconTint = ContextCompat.getColor(context, R.color.note_block_highlighted_text_0)
        }

        iconImageView.setColorFilter(iconTint)
        titleTextView.setTextColor(text0Color)
        countTextView.setTextColor(text1Color)

    }

    private fun assignData(folder: Folder) {
        titleTextView.text = folder.title
        countTextView.text = if (folder.count == 1) {
            folder.count.toString() + " notes"
        } else {
            folder.count.toString() + " note"
        }

        setHighlight(folder.isHighlighted)
    }

}