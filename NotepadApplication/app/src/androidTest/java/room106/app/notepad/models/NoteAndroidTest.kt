package room106.app.notepad.models

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class NoteAndroidTest {

    private val context = ApplicationProvider.getApplicationContext<Context>()

    @Test
    fun nextUniqueIdIsIncrementedByOne_True() {
        val note = Note()
        val noteID1 = note.assignUniqueId(context)
        val noteID2 = note.assignUniqueId(context)

        assertThat(noteID1).isEqualTo(noteID2 - 1)
    }

    @Test
    fun uniqueIdSameAsId_True() {
        val note = Note()
        val assignedId = note.assignUniqueId(context)
        assertThat(assignedId).isEqualTo(note.id)
    }
}