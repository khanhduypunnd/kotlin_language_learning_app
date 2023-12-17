package com.example.learnlanguage.database


import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.learnlanguage.model.Folder
import com.example.learnlanguage.model.Topic
import com.example.learnlanguage.model.Word


class AppDatabase(context: Context?) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE folder (" +
                    "folder_id INTEGER PRIMARY KEY," +
                    "folder_name TEXT NOT NULL)"
        )

        db.execSQL(
            "CREATE TABLE topic (" +
                    "topic_id INTEGER PRIMARY KEY," +
                    "topic_name TEXT NOT NULL," +
                    "total INTEGER," +
                    "mode INTEGER," +
                    "folder_id INTEGER," +
                    "FOREIGN KEY (folder_id) REFERENCES folder(folder_id))"
        )

        db.execSQL(
            "CREATE TABLE word (" +
                    "word_id INTEGER PRIMARY KEY," +
                    "word_name TEXT NOT NULL," +
                    "meaning TEXT NOT NULL," +
                    "state INTEGER," +
                    "topic_id INTEGER," +
                    "FOREIGN KEY (topic_id) REFERENCES topic(topic_id))")
        db.execSQL(
            "INSERT INTO folder (folder_name) VALUES " +
                    "('Folder 1')," +
                    "('Folder 2')," +
                    "('Folder 3');"
        );

        db.execSQL(
            "INSERT INTO topic (topic_name, total, mode, folder_id) VALUES " +
                    "('Lời chào', 2, 0, 1)," +
                    "('Phương tiện', 2, 0, 1)," +
                    "('Dụng cụ học tập', 2, 0, 2)," +
                    "('Thời trang', 2, 0, 2)," +
                    "('Hoa quả', 2, 0, 3);"
        );

        db.execSQL(
            "INSERT INTO word (word_name, meaning, state, topic_id) VALUES " +
                    "('hello', 'xin chào', 0, 1)," +
                    "('bye', 'tạm biệt', 0, 1)," +
                    "('car', 'ô tô', 0, 2)," +
                    "('bicycle', 'xe đạp', 0, 2)," +
                    "('pencil', 'bút chì', 0, 3)," +
                    "('notebook', 'sổ tay', 0, 3)," +
                    "('dress', 'váy', 0, 4)," +
                    "('shirt', 'áo sơ mi', 0, 4)," +
                    "('apple', 'táo', 0, 5)," +
                    "('banana', 'chuối', 0, 5);"
        );



    }



    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

    }

    companion object {


        private val DATABASE_NAME = "learnLanguage"
        private val DATABASE_VERSION = 1
    }

    override fun onOpen(db: SQLiteDatabase?) {
        super.onOpen(db)
    }

    fun getAllFolder(): List<Folder> {
        val list = arrayListOf<Folder>()
        val st = readableDatabase
        val order = "folder_id DESC"
        val rs = st.query(
            "folder", null, null,
            null, null, null, order
        )
        while (rs != null && rs.moveToNext()) {
            val id = rs.getInt(0)
            val name = rs.getString(1)
            list.add(Folder(id, name))
        }
        return list
    }
    fun addWord(word: Word, id : Int): Long {
        val values = ContentValues()
        values.put("word_name", word.name)
        values.put("meaning", word.meaning)
        values.put("state", word.learState)
        values.put("topic_id", id)
        val sqLiteDatabase = writableDatabase
        return sqLiteDatabase.insert("word", null, values)
    }

    fun updateTopic(topic: Topic): Int {
        val values = ContentValues()
        values.put("topic_name", topic.name)
        values.put("total", topic.total)
        values.put("mode", topic.mode)
        val sqLiteDatabase = writableDatabase
        val whereClause = "topic_id= ?"
        val whereArgs = arrayOf(topic.id.toString())
        return sqLiteDatabase.update("topic", values, whereClause, whereArgs)
    }
    fun updateWord(word: Word): Int {
        val values = ContentValues()
        values.put("word_name", word.name)
        values.put("meaning", word.meaning)
        values.put("state", word.learState)
        val sqLiteDatabase = writableDatabase
        val whereClause = "word_id= ?"
        val whereArgs = arrayOf(word.id.toString())
        return sqLiteDatabase.update("word", values, whereClause, whereArgs)
    }
    fun addFolder(folder: Folder): Long {
        val values = ContentValues()
        values.put("folder_name", folder.name)
        val sqLiteDatabase = writableDatabase
        return sqLiteDatabase.insert("folder", null, values)
    }
    fun getAllTopic(): List<Topic> {
        val list = arrayListOf<Topic>()
        val st = readableDatabase
        val order = "topic_id DESC"
        val rs = st.query(
            "topic", null, null,
            null, null, null, order
        )
        while (rs != null && rs.moveToNext()) {
            val id = rs.getInt(0)
            val name = rs.getString(1)
            val total = rs.getInt(2)
            val mode = rs.getInt(3)
            list.add(Topic(id, name,total, mode))
        }
        return list
    }
    fun getAllWordTopic(): List<Word> {
        val list = arrayListOf<Word>()
        val st = readableDatabase
        val order = "word_id DESC"
        val rs = st.query(
            "word", null, null,
            null, null, null, order
        )
        while (rs != null && rs.moveToNext()) {
            val id = rs.getInt(0)
            val name = rs.getString(1)
            val meaning = rs.getString(2)
            val state = rs.getInt(3)
            list.add(Word(id, name,meaning, state))
        }
        return list
    }
    fun addTopic(topic: Topic, id : Int): Long {
        val values = ContentValues()
        values.put("topic_name", topic.name)
        values.put("total", topic.total)
        values.put("mode", topic.mode)
        values.put("folder_id", id)
        val sqLiteDatabase = writableDatabase
        return sqLiteDatabase.insert("topic", null, values)
    }
    fun searchByWordTopic(key: Int): List<Word> {
        val list = arrayListOf<Word>()
        val whereClause = "topic_id like ?"
        val whereArgs = arrayOf("%$key%")
        val st = readableDatabase
        val rs = st.query("word", null, whereClause, whereArgs, null, null, null)
        while (rs != null && rs.moveToNext()) {
            val id = rs.getInt(0)
            val name = rs.getString(1)
            val meaning = rs.getString(2)
            val state = rs.getInt(3)
            list.add(Word(id, name,meaning,state))
        }
        return list
    }

    fun searchByNameFolder(key: String): List<Folder> {
        val list = arrayListOf<Folder>()
        val whereClause = "folder_name like ?"
        val whereArgs = arrayOf("%$key%")
        val st = readableDatabase
        val rs = st.query("folder", null, whereClause, whereArgs, null, null, null)
        while (rs != null && rs.moveToNext()) {
            val id = rs.getInt(0)
            val name = rs.getString(1)
            list.add(Folder(id, name))
        }
        return list
    }

    fun searchByTopicByFolderId(id: Int): List<Topic> {
        val list = arrayListOf<Topic>()
        val whereClause = "folder_id like ?"
        val whereArgs = arrayOf("%$id%")
        val st = readableDatabase
        val rs = st.query("topic", null, whereClause, whereArgs, null, null, null)
        while (rs != null && rs.moveToNext()) {
            val id = rs.getInt(0)
            val name = rs.getString(1)
            val total = rs.getInt(2)
            val mode = rs.getInt(3)
            list.add(Topic(id, name,total,mode))
        }
        return list
    }
    fun deleteTopic(id: Int): Int {
        val whereClause = "topic_id= ?"
        val whereArgs = arrayOf(Integer.toString(id))
        val sqLiteDatabase = writableDatabase
        return sqLiteDatabase.delete("topic", whereClause, whereArgs)
    }
    fun deleteFolder(id: Int): Int {
        val whereClause = "folder_id= ?"
        val whereArgs = arrayOf(Integer.toString(id))
        val sqLiteDatabase = writableDatabase
        return sqLiteDatabase.delete("folder", whereClause, whereArgs)
    }
    fun deleteWord(id: Int): Int {
        val sqLiteDatabase = writableDatabase
        val whereClause = "topic_id = ?"
        val whereArgs = arrayOf(Integer.toString(id))
        return sqLiteDatabase.delete("word", whereClause, whereArgs)
    }

    fun deleteSingleWord(id: Int): Int {
        val sqLiteDatabase = writableDatabase
        val whereClause = "word_id = ?"
        val whereArgs = arrayOf(Integer.toString(id))
        return sqLiteDatabase.delete("word", whereClause, whereArgs)
    }


}
