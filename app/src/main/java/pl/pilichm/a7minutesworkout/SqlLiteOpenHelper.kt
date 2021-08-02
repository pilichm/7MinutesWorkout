package pl.pilichm.a7minutesworkout

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SqlLiteOpenHelper(context: Context, factory: SQLiteDatabase.CursorFactory?)
    : SQLiteOpenHelper(context, DB_NAME, factory, DB_VERSION) {

    companion object {
        private const val DB_VERSION = 1
        private const val DB_NAME = "SevenMinutes.db"
        private const val TABLE_HISTORY = "HISTORY"
        private const val COL_ID = "_ID"
        private const val COL_COMPLETED_DATE = "COMPLETED_DATE"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createQuery = "CREATE TABLE $TABLE_HISTORY (" +
                " $COL_ID INTEGER PRIMARY KEY, $COL_COMPLETED_DATE TEXT )"
        db?.execSQL(createQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_HISTORY")
        onCreate(db)
    }

    fun addDate(date: String){
        val values = ContentValues()
        values.put(COL_COMPLETED_DATE, date)
        val db = this.writableDatabase
        db.insert(TABLE_HISTORY, null, values)
        db.close()
    }

    fun getAllCompletedDays(): ArrayList<String>{
        val results = ArrayList<String>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_HISTORY", null)

        while (cursor.moveToNext()){
            val dateValue = cursor.getString(cursor.getColumnIndex(COL_COMPLETED_DATE))
            results.add(dateValue)
        }
        cursor.close()

        return results
    }

}