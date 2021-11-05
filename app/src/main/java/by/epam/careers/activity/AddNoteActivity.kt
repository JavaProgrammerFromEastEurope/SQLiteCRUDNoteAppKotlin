package by.epam.careers.activity

import android.annotation.SuppressLint
import android.content.ContentValues
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import by.epam.careers.R
import by.epam.careers.database.DBManager
import by.epam.careers.databinding.ActivityAddBinding
import java.text.SimpleDateFormat
import java.util.*

class AddNoteActivity : AppCompatActivity() {

    private var id = 0
    private lateinit var binding: ActivityAddBinding

    @SuppressLint("SimpleDateFormat")
    var formatter: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)

        setContentView(R.layout.activity_add)
        findViewById<EditText>(R.id.titleET).addTextChangedListener(textWatcher)
        findViewById<EditText>(R.id.descET).addTextChangedListener(textWatcher)
        try {
            val bundle: Bundle? = intent.extras
            if (bundle != null) {
                id = bundle.getInt("ID", 0)
                supportActionBar!!.title = "Update Note"
                findViewById<EditText>(R.id.titleET).setText(bundle.getString("name"))
                findViewById<EditText>(R.id.descET).setText(bundle.getString("des"))
                findViewById<Button>(R.id.addNoteBtn)
                    .setOnClickListener(editNoteListener)
                findViewById<Button>(R.id.addNoteBtn).text = "Update"
                makeAddNoteUnEnabled()
            } else {
                supportActionBar!!.title = "Add Note"
                findViewById<Button>(R.id.addNoteBtn).text = "Add"
                findViewById<Button>(R.id.addNoteBtn)
                    .setOnClickListener(addNoteListener)
                makeAddNoteUnEnabled()
            }
        } catch (e: RuntimeException) {
        }

    }

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable?) {

            if (findViewById<EditText>(R.id.titleET).text.isNullOrEmpty() ||
                findViewById<EditText>(R.id.descET).text.isNullOrEmpty()
            ) {
                makeAddNoteUnEnabled()
            } else {
                makeAddNoteEnabled()
            }

        }
    }

    private fun makeAddNoteEnabled() {
        this@AddNoteActivity.findViewById<Button>(R.id.addNoteBtn).isEnabled = true
        1f.also { findViewById<Button>(R.id.addNoteBtn).alpha = it }
    }

    private fun makeAddNoteUnEnabled() {
        this@AddNoteActivity.findViewById<Button>(R.id.addNoteBtn).isEnabled = false
        0.5f.also { findViewById<Button>(R.id.addNoteBtn).alpha = it }
    }

    private val addNoteListener = View.OnClickListener {
        val dbManager = DBManager(this)
        val values = ContentValues()
        values.put("Title", findViewById<TextView>(R.id.titleET).text.toString())
        values.put("Description", findViewById<TextView>(R.id.descET).text.toString())
        values.put("Tag", " ")
        values.put("Date", formatter.format(Date(System.currentTimeMillis())))
        try {
            dbManager.insert(values)
            finish()
            Toast.makeText(this, "Note is added", Toast.LENGTH_SHORT).show()
        } catch (e: RuntimeException) {
            Toast.makeText(this, "Error adding note!", Toast.LENGTH_SHORT).show()
        }
    }

    private val editNoteListener = View.OnClickListener {
        val dbManager = DBManager(this)
        val values = ContentValues()
        values.put("Title", findViewById<TextView>(R.id.titleET).text.toString())
        values.put("Description", findViewById<TextView>(R.id.descET).text.toString())
        values.put("Tag", " ")
        values.put("Date", formatter.format(Date(System.currentTimeMillis())))
        try {
            val selectionArgs = arrayOf(id.toString())
            dbManager.update(values, "ID = ?", selectionArgs)
            finish()
            Toast.makeText(this, "Note is updated", Toast.LENGTH_SHORT).show()
        } catch (e: RuntimeException) {
            Toast.makeText(this, "Error updating note!", Toast.LENGTH_SHORT).show()
        }
    }
}