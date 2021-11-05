package by.epam.careers.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteQueryBuilder
import android.widget.Toast

class DBManager(context: Context) {

    /**SQLite Database name**/
    private val dbName  = "Notes"
    private val dbTable = "Notes"

    /**Columns**/
    private val colID    = "ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT"
    private val colTitle = "Title TEXT"
    private val colDes   = "Description TEXT"
    private val colTag   = "Tag TEXT"
    private val colDate  = "Date TEXT"

    /**Database version**/
    var dbVersion = 1

    val sqlOnCreate = "CREATE TABLE IF NOT EXISTS $dbTable " +
            "($colID, $colTitle, $colDes, $colTag, $colDate)"

    val sqlOnUpdate = "DROP TABLE IF EXISTS $dbTable"

    private var sqlDB: SQLiteDatabase? = null

    init {
        val db = DatabaseHelperNotes(context)
        sqlDB = db.writableDatabase
    }

    inner class DatabaseHelperNotes(context: Context) :
        SQLiteOpenHelper(context, dbName, null, dbVersion) {
        private var context: Context? = context

        override fun onCreate(db: SQLiteDatabase?) {
            db!!.execSQL(sqlOnCreate)
            Toast.makeText(this.context, "database created...", Toast.LENGTH_SHORT).show()
        }

        override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
            db!!.execSQL(sqlOnUpdate)
        }
    }

    fun query(
        projection: Array<String>,
        selection: String,
        selectionArgs: Array<String>,
        sortOrder: String): Cursor? {
        val qb = SQLiteQueryBuilder()
        qb.tables = dbTable
        return qb.query(sqlDB, projection, selection, selectionArgs, null, null, sortOrder)
    }

    fun insert(values: ContentValues): Long {
        return sqlDB!!.insert(dbTable, "", values)
    }

    fun update(values: ContentValues, selection: String, selectionArgs: Array<String>): Int {
        return sqlDB!!.update(dbTable, values, selection, selectionArgs)
    }

    fun delete(selection: String, selectionArgs: Array<String>): Int {
        return sqlDB!!.delete(dbTable, selection, selectionArgs)
    }
}