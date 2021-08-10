package room106.app.notepad.models


import com.google.common.truth.Truth.assertThat
import org.junit.Test

class NoteTest {

    @Test
    fun `blank title returns false`() {
        val result = Note().apply {
            // title is blank by default
            body = "Some random body"
        }.isNotBlank()

        assertThat(result).isFalse()
    }

    @Test
    fun `blank body and list returns false`() {
        val result = Note().apply {
            title = "Title" // Valid title
            // By default, body and tasks list are empty
        }.isNotBlank()

        assertThat(result).isFalse()
    }

    @Test
    fun `valid body returns true`() {
        val result = Note().apply {
            title = "Title" // Valid title
            body = "Some random body" // Valid body
        }.isNotBlank()

        assertThat(result).isTrue()
    }

    @Test
    fun `valid list returns true`() {
        val result = Note().apply {
            title = "Title" // Valid title
            tasks.add(Task("Title", false)) // Valid tasks list
        }.isNotBlank()

        assertThat(result).isTrue()
    }
}