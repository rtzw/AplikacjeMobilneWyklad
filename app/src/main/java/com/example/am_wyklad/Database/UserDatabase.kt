package com.example.am_wyklad.Database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class UserDatabase(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null,
    DATABASE_VERSION) {

    companion object{
        private const val DATABASE_NAME = "am.db"
        private const val DATABASE_VERSION = 1

        private const val TABLE_USER = "user"
        private const val COLUMN_ID = "id"
        private const val COLUMN_USER_LOGIN = "login"
        private const val COLUMN_USER_PASSWORD = "password"
        private const val COLUMN_USER_NAME = "name"

        private const val TABLE_CHALLANGE = "profile"
        private const val COLUMN_CHALLANGE_USER_ID = "user_id"
        private const val COLUMN_CHALLANGE_TOPIC = "topic"
        private const val COLUMN_CHALLANGE_DESCRIPTION = "description"

        private const val TABLE_PROFILE = "challange"
        private const val COLUMN_PROFILE_ADMIN_ID = "admin_id"
        private const val COLUMN_PROFILE_CODE = "code"
        private const val COLUMN_PROFILE_CHALLANGES= "challenges"
        private const val COLUMN_PROFILE_PLAYERS= "players"


    }
    private val CREATE_USER_TABLE = ("CREATE TABLE  " + TABLE_USER + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_USER_LOGIN + " TEXT,"
            + COLUMN_USER_PASSWORD + " TEXT, " +  COLUMN_USER_NAME + " TEXT" + ")")

    private val CREATE_PROFILE_TABLE = ("CREATE TABLE  " + TABLE_PROFILE + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_PROFILE_ADMIN_ID + " INTEGER,"
            + COLUMN_PROFILE_CODE + " TEXT, " +  COLUMN_PROFILE_CHALLANGES + " TEXT" + COLUMN_PROFILE_PLAYERS + " TEXT" + ")")

    private val CREATE_CHALLANGE_TABLE = ("CREATE TABLE  " + TABLE_CHALLANGE + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_CHALLANGE_USER_ID + " INTEGER,"
            + COLUMN_CHALLANGE_TOPIC + " TEXT, " +  COLUMN_CHALLANGE_DESCRIPTION + " TEXT" + ")")

    private val DROP_USER_TABLE = "DROP TABLE IF EXISTS $TABLE_USER"
    private val DROP_PROFILE_TABLE = "DROP TABLE IF EXISTS $TABLE_PROFILE"
    private val DROP_CHALLANGE_TABLE = "DROP TABLE IF EXISTS $TABLE_CHALLANGE"

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_USER_TABLE)
        db.execSQL(CREATE_PROFILE_TABLE)
        db.execSQL(CREATE_CHALLANGE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, old: Int, new: Int) {
        db.execSQL(DROP_USER_TABLE)
        db.execSQL(DROP_PROFILE_TABLE)
        db.execSQL(DROP_CHALLANGE_TABLE)
        onCreate(db)
    }


    fun getAllUser(): List<User> {
        val columns = arrayOf(COLUMN_ID, COLUMN_USER_LOGIN, COLUMN_USER_PASSWORD,
            COLUMN_USER_NAME)
        val sortOrder = "$COLUMN_USER_LOGIN ASC"
        val userList = ArrayList<User>()
        val db = this.readableDatabase
        // query the user table
        val cursor = db.query(TABLE_USER, //Table to query
            columns,            //columns to return
            null,     //columns for the WHERE clause
            null,  //The values for the WHERE clause
            null,      //group the rows
            null,       //filter by row groups
            sortOrder)         //The sort order
        if (cursor.moveToFirst()) {
            do {
                val user = User(id = cursor.getString(cursor.getColumnIndex(COLUMN_ID)).toInt(),
                    login = cursor.getString(cursor.getColumnIndex(COLUMN_USER_LOGIN)),
                    password = cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD)),
                    name = cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)))
                userList.add(user)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return userList
    }

    fun addUser(user: User) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_USER_LOGIN, user.login)
        values.put(COLUMN_USER_PASSWORD, user.password)
        values.put(COLUMN_USER_NAME, user.name)
        // Inserting Row
        db.insert(TABLE_USER, null, values)
        db.close()
    }

    fun getUserByLogin(login: String): ArrayList<User> {
        val users: ArrayList<User> = ArrayList<User>()
        val db = this.readableDatabase
        try {

            val query = db.rawQuery ("select * from user u where u.login = + '$login'", null);
                if (!query.moveToFirst())
                    return ArrayList();

                do {
                    users.add(User (query.getInt(0),query.getString(1), query.getString(2),query.getString(3)));
                } while (query.moveToNext());

        } finally {
            db.close();
            return users
        }
    }

    fun isRegistered(login: String, password: String): Boolean {
        val db = this.readableDatabase
        val query = db.rawQuery ("select * from user u where u.login = '$login' and u.password = '$password'", null);
        return query.moveToFirst()
    }

    fun isRegistered(login: String): Boolean {
        val db = this.readableDatabase
        val query = db.rawQuery ("select * from user u where u.login = '$login' ", null);
        return query.moveToFirst()

    }

}