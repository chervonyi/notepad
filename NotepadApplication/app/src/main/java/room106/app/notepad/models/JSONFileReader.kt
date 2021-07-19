package room106.app.notepad.models

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.annotations.Expose
import java.io.*

class JSONFileReader {

    @Suppress("PrivatePropertyName")
    private val FILENAME = "notes.json"

    fun saveVault(context: Context, json: String): Boolean {
        return try {
            val fos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE)
            fos.write(json.toByteArray())
            fos.close()
            true
        } catch (fileNotFound: FileNotFoundException) {
            false
        } catch (ioException: IOException) {
            false
        }
    }

//    private var _vault: Vault? = null
//    var vault: Vault
//        private set(value) {}
//        get() {
//            if (_vault == null) {
//                _vault = readVault()
//            }
//        }

    fun readVault(context: Context): Vault {
        try {
            val fis = context.openFileInput(FILENAME)
            val isr = InputStreamReader(fis)
            val bufferedReader = BufferedReader(isr)
            val sb = StringBuilder()

            var line = bufferedReader.readLine()

            while (line != null) {
                sb.append(line)
                line = bufferedReader.readLine()
            }

            Log.d("Test", "readNotesFromJSONFile: $sb")
            if (sb.isEmpty()) {
                return Vault()
            }
//            return Gson().fromJson(sb.toString(), Vault::class.java)

            val gson = GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()
            return gson.fromJson(sb.toString(), Vault::class.java)

        } catch (fileNotFound: FileNotFoundException) {
            return Vault()
        } catch (ioException: IOException) {
            return Vault()
        }
    }

}