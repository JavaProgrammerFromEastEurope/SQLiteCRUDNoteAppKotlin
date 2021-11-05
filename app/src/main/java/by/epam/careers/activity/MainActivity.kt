package by.epam.careers.activity

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.SearchManager
import android.content.*
import android.content.ClipDescription
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageButton
import by.epam.careers.R
import by.epam.careers.database.DBManager
import by.epam.careers.databinding.ActivityAddBinding
import by.epam.careers.databinding.ActivityMainBinding
import by.epam.careers.entity.Note
import java.sql.DriverManager.println
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var noteList = ArrayList<Note>()
    private var mSharedPref: SharedPreferences? = null
    private val projection = arrayOf("ID", "Title", "Description", "Tag", "Date")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(R.layout.activity_main)
        mSharedPref = this.getSharedPreferences("My_Data", Context.MODE_PRIVATE)
        loadSharedPref()
    }

    override fun onResume() {
        super.onResume()
        loadSharedPref()
    }

    private fun loadSharedPref() {
        val mSorting = mSharedPref!!.getString("Sort", "newest")
        when (mSorting) {
            "newest" -> "%".loadQueryNewest()
            "oldest" -> "%".loadQueryOldest()
            "ascending" -> "%".loadQueryAscending()
            "descending" -> "%".loadQueryDescending()
            "tag" -> "%".loadQueryTag()
        }
    }

    @SuppressLint("Range")
    private fun String.loadQueryNewest() {
        val dbManager = DBManager(this@MainActivity)
        val selectionArgs = arrayOf(this)
        val string = "Last update: "
        val cursor = dbManager.query(projection,
            "Date like ?", selectionArgs, "Date")
        noteList.clear()
        if (cursor!!.moveToLast()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex("ID"))
                val title = cursor.getString(cursor.getColumnIndex("Title"))
                val description = cursor.getString(cursor.getColumnIndex("Description"))
                val tags = cursor.getString(cursor.getColumnIndex("Tag"))
                val date = cursor.getString(cursor.getColumnIndex("Date"))
                noteList.add(Note(id, title, description, tags, string + date))
            } while (cursor.moveToPrevious())
        }
        val notesAdapter = MyNotesAdapter( noteList)
        findViewById<ListView>(R.id.notesLV).adapter = notesAdapter
        val mActionBar = supportActionBar
        if (mActionBar != null) {
            mActionBar.subtitle = "Total ${notesAdapter.count} notes..."
        }
    }

    @SuppressLint("Range")
    private fun String.loadQueryOldest() {
        val dbManager = DBManager(this@MainActivity)
        val selectionArgs = arrayOf(this)
        val string = "Last update: "
        val cursor = dbManager.query(projection,
            "Date like ?", selectionArgs, "Date")
        noteList.clear()
        if (cursor!!.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex("ID"))
                val title = cursor.getString(cursor.getColumnIndex("Title"))
                val description = cursor.getString(cursor.getColumnIndex("Description"))
                val tags = cursor.getString(cursor.getColumnIndex("Tag"))
                val date = cursor.getString(cursor.getColumnIndex("Date"))
                noteList.add(Note(id, title, description, tags, string + date))
            } while (cursor.moveToNext())

        }
        val notesAdapter = MyNotesAdapter(noteList)
        findViewById<ListView>(R.id.notesLV).adapter = notesAdapter
        val mActionBar = supportActionBar
        if (mActionBar != null) {
            mActionBar.subtitle = "Total ${notesAdapter.count} notes..."
        }
    }

    @SuppressLint("Range")
    private fun String.loadQueryAscending() {
        val dbManager = DBManager(this@MainActivity)
        val selectionArgs = arrayOf(this)
        val string = "Last update: "
        val cursor = dbManager.query(projection,
            "Title like ?", selectionArgs, "Title")
        noteList.clear()
        if (cursor!!.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex("ID"))
                val title = cursor.getString(cursor.getColumnIndex("Title"))
                val description = cursor.getString(cursor.getColumnIndex("Description"))
                val tags = cursor.getString(cursor.getColumnIndex("Tag"))
                val date = cursor.getString(cursor.getColumnIndex("Date"))
                noteList.add(Note(id, title, description, tags, string + date))
            } while (cursor.moveToNext())

        }
        val notesAdapter = MyNotesAdapter(noteList)
        findViewById<ListView>(R.id.notesLV).adapter = notesAdapter
        val mActionBar = supportActionBar
        if (mActionBar != null) {
            mActionBar.subtitle = "Total ${notesAdapter.count} notes..."
        }
    }

    @SuppressLint("Range")
    private fun String.loadQueryDescending() {
        val dbManager = DBManager(this@MainActivity)
        val selectionArgs = arrayOf(this)
        val lastUpdate = "Last update: "
        val cursor = dbManager.query(projection,
            "Title like ?", selectionArgs, "Title")
        noteList.clear()
        if (cursor!!.moveToLast()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex("ID"))
                val title = cursor.getString(cursor.getColumnIndex("Title"))
                val description = cursor.getString(cursor.getColumnIndex("Description"))
                val tags = cursor.getString(cursor.getColumnIndex("Tag"))
                val date = cursor.getString(cursor.getColumnIndex("Date"))
                noteList.add(Note(id, title, description, tags, "$lastUpdate$date"))
            } while (cursor.moveToPrevious())
        }
        val notesAdapter = MyNotesAdapter(noteList)
        findViewById<ListView>(R.id.notesLV).adapter = notesAdapter
        val mActionBar = supportActionBar
        if (mActionBar != null) {
            mActionBar.subtitle = "Total ${notesAdapter.count} notes..."
        }
    }

    @SuppressLint("Range")
    private fun String.loadQueryTag() {
        val dbManager = DBManager(this@MainActivity)
        val projection = arrayOf("ID", "Title", "Description", "Tag", "Date")
        val selectionArgs = arrayOf(this)
        val string = "Last update: "
        val cursor = dbManager.query(projection,
            "Tag like ?", selectionArgs, "Tag")
        noteList.clear()
        if (cursor!!.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex("ID"))
                val title = cursor.getString(cursor.getColumnIndex("Title"))
                val description = cursor.getString(cursor.getColumnIndex("Description"))
                val tags = cursor.getString(cursor.getColumnIndex("Tag"))
                val date = cursor.getString(cursor.getColumnIndex("Date"))
                noteList.add(Note(id, title, description, tags, string + date))
            } while (cursor.moveToNext())
        }
        val notesAdapter = MyNotesAdapter(noteList)
        findViewById<ListView>(R.id.notesLV).adapter = notesAdapter
        val mActionBar = supportActionBar
        if (mActionBar != null) {
            mActionBar.subtitle = "Total ${notesAdapter.count} notes"
        }
    }

    @SuppressLint("Range")
    private fun loadSearchQuery(query: String) {

        val dbManager = DBManager(this)
        val projection = arrayOf("*")
        val selectionArgs = arrayOf(query)
        val string = "Last update: "
        val cursor = dbManager.query(projection,
            "(Description LIKE ?) OR (Title LIKE ?)", selectionArgs, "Title")
        noteList.clear()
        if (cursor!!.moveToLast()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex("ID"))
                val title1 = cursor.getString(cursor.getColumnIndex("Title"))
                val description = cursor.getString(cursor.getColumnIndex("Description"))
                val tags = cursor.getString(cursor.getColumnIndex("Tag"))
                val date = cursor.getString(cursor.getColumnIndex("Date"))
                noteList.add(Note(id, title1, description, tags, string + date))
            } while (cursor.moveToPrevious())
        }
        val notesAdapter = MyNotesAdapter(noteList)
        findViewById<ListView>(R.id.notesLV).adapter = notesAdapter
        val mActionBar = supportActionBar
        if (mActionBar != null) {
            mActionBar.subtitle = "Total ${notesAdapter.count} notes..."
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        val sv: SearchView = menu!!.findItem(R.id.app_bar_search).actionView as SearchView
        sv.maxWidth = 800
        val sm = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        sv.setSearchableInfo(sm.getSearchableInfo(componentName))
        sv.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                "%$query%".loadQueryAscending()
                if (noteList.size == 0) loadSearchQuery("%$query%")
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                "%$query%".loadQueryAscending()
                if (noteList.size == 0) loadSearchQuery("%$query%")
                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_addNote -> {
                startActivity(Intent(this, AddNoteActivity::class.java))
            }
            R.id.action_sort -> {
                showSortDialog()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showTagDialog(note: Note) {
        val dbManager = DBManager(this)
        val tagOptions = arrayOf("#Work", "#Study", "#Entertainment", "#Important", "#Clear")
        val selectionArgs = arrayOf(note.nodeID.toString())
        val values = ContentValues()
        val mBuilder = AlertDialog.Builder(this)
        mBuilder.setTitle("Tags")
        mBuilder.setIcon(R.drawable.ic_action_tags)
        mBuilder.setSingleChoiceItems(tagOptions, -1) { dialogInterface, i ->
            when (i) {
                0 -> {
                    values.put("Tag", " #Work ")
                    dbManager.update(values, "ID = ?", selectionArgs)
                    "%".loadQueryNewest()
                    Toast.makeText(this, "#Work", Toast.LENGTH_SHORT).show()
                }
                1 -> {
                    values.put("Tag", " #Study ")
                    dbManager.update(values, "ID = ?", selectionArgs)
                    "%".loadQueryNewest()
                    Toast.makeText(this, "#Study", Toast.LENGTH_SHORT).show()
                }
                2 -> {
                    values.put("Tag", " #Entertainment ")
                    dbManager.update(values, "ID = ?", selectionArgs)
                    "%".loadQueryNewest()
                    Toast.makeText(this, "#Entertainment", Toast.LENGTH_SHORT).show()
                }
                3 -> {
                    values.put("Tag", " #Important ")
                    dbManager.update(values, "ID = ?", selectionArgs)
                    "%".loadQueryNewest()
                    Toast.makeText(this, "#Important", Toast.LENGTH_SHORT).show()
                }
                4 -> {
                    values.put("Tag", " ")
                    dbManager.update(values, "ID = ?", selectionArgs)
                    "%".loadQueryNewest()
                    Toast.makeText(this, "Teg was removed", Toast.LENGTH_SHORT).show()
                }
            }
            dialogInterface.dismiss()
        }
        mBuilder.create().show()
    }

    private fun showSortDialog() {
        val sortOptions = arrayOf("New", "Old", "Title (A->Z)", "Title (Z->A)", "Label")
        val mBuilder = AlertDialog.Builder(this)
        mBuilder.setTitle("Sorting ")
        mBuilder.setIcon(R.drawable.ic_action_sort)
        mBuilder.setSingleChoiceItems(sortOptions, -1) { dialogInterface, i ->
            when (i) {
                0 -> {
                    val editor = mSharedPref!!.edit()
                    editor.putString("Sort", "newest").apply()
                    "%".loadQueryNewest()
                    Toast.makeText(this, "newest", Toast.LENGTH_SHORT).show()
                }
                1 -> {
                    val editor = mSharedPref!!.edit()
                    editor.putString("Sort", "oldest").apply()
                    "%".loadQueryOldest()
                    Toast.makeText(this, "oldest", Toast.LENGTH_SHORT).show()
                }
                2 -> {
                    val editor = mSharedPref!!.edit()
                    editor.putString("Sort", "ascending").apply()
                    "%".loadQueryAscending()
                    Toast.makeText(this, "By title (А->Z)", Toast.LENGTH_SHORT).show()
                }
                3 -> {
                    val editor = mSharedPref!!.edit()
                    editor.putString("Sort", "descending").apply()
                    "%".loadQueryDescending()
                    Toast.makeText(this, "By title (Z->А)", Toast.LENGTH_SHORT).show()
                }
                4 -> {
                    val editor = mSharedPref!!.edit()
                    editor.putString("Sort", "tag").apply()
                    "%".loadQueryTag()
                    Toast.makeText(this, "By Label", Toast.LENGTH_SHORT).show()
                }
            }
            dialogInterface.dismiss()
        }
        mBuilder.create().show()
    }

    inner class MyNotesAdapter
        (private val listNotes: ArrayList<Note>) : BaseAdapter() {

        @SuppressLint("ViewHolder")
        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

            val rowView = layoutInflater.inflate(R.layout.activity_row, parent, false)
            val note = listNotes[position]

            rowView.findViewById<TextView>(R.id.titleTV).text = note.nodeName
            rowView.findViewById<TextView>(R.id.descrTV).text = note.nodeDes
            rowView.findViewById<TextView>(R.id.tagsTV).text = note.nodeTag
            rowView.findViewById<TextView>(R.id.dateTV).text = note.nodeDate

            rowView.findViewById<AppCompatImageButton>(R.id.tagsBtn)
                .setOnClickListener{showTagDialog(note)}
            rowView.findViewById<AppCompatImageButton>(R.id.deleteBtn)
                .setOnClickListener{showDeleteDialog(note)}
            rowView.findViewById<AppCompatImageButton>(R.id.editBtn)
                .setOnClickListener{updateNoteAction(note)}
            rowView.findViewById<AppCompatImageButton>(R.id.copyBtn)
                .setOnClickListener{copyNoteAction(rowView)}
            rowView.findViewById<AppCompatImageButton>(R.id.shareBtn)
                .setOnClickListener{shareNoteAction(rowView)}

            return rowView
        }

        override fun getItem(position: Int): Any {
            return listNotes[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return listNotes.size
        }
    }

    private fun updateNoteAction(note: Note) {
        val intent = Intent(this, AddNoteActivity::class.java)
        intent.putExtra("ID", note.nodeID)
        intent.putExtra("name", note.nodeName)
        intent.putExtra("des", note.nodeDes)
        startActivity(intent)
    }

    private fun showDeleteDialog(note: Note) {
        val dbManager = DBManager(this)
        val selectionArgs = arrayOf(note.nodeID.toString())
        val removeNoteDialogListener = DialogInterface.OnClickListener { _, i ->
            when(i){
                AlertDialog.BUTTON_POSITIVE ->{
                    dbManager.delete("ID = ?", selectionArgs)
                    "%".loadQueryAscending()
                    Toast.makeText(this,
                        "Note is removed!", Toast.LENGTH_SHORT).show()}
                AlertDialog.BUTTON_NEGATIVE ->{}
            }
        }
        val mBuilder = AlertDialog.Builder(this).apply {
            setTitle("${getString(R.string.del_dlg_note_ttl)} ${note.nodeName}!")
            setIcon(R.drawable.ic_action_alert)
            setMessage(getString(R.string.del_dlg_note_msg))
            setPositiveButton(getString(R.string.del_dlg_ok_btn),  removeNoteDialogListener)
            setNegativeButton(getString(R.string.del_dlg_ccl_btn), removeNoteDialogListener)
        }
        mBuilder.create().show()
    }

    private fun copyNoteAction(view:View) {
        val title = view.findViewById<TextView>(R.id.titleTV).text.toString()
        val desc =  view.findViewById<TextView>(R.id.descrTV).text.toString()
        val clip = ClipData(
            ClipDescription("description", arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN)),
            ClipData.Item("$title\n$desc"))
        val cb = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        cb.setPrimaryClip(clip)
        Toast.makeText(this, "Copied...", Toast.LENGTH_SHORT).show()
    }

    private fun shareNoteAction(view:View) {
        val title = view.findViewById<TextView>(R.id.titleTV).text.toString()
        val desc =  view.findViewById<TextView>(R.id.descrTV).text.toString()
        val text = "$title\n$desc"
        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, text)
        startActivity(Intent.createChooser(shareIntent, text))
    }
}