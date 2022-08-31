package com.example.am_wyklad.Database

import android.content.ClipDescription
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class UserDatabase(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null,
    DATABASE_VERSION) {

    companion object{
        private const val DATABASE_NAME = "am.db"
        private const val DATABASE_VERSION = 11

        private const val TABLE_USER = "user"
        private const val COLUMN_ID = "id"
        private const val COLUMN_USER_LOGIN = "login"
        private const val COLUMN_USER_PASSWORD = "password"
        private const val COLUMN_USER_NAME = "name"

        private const val TABLE_CHALLENGE = "challenge"
        private const val COLUMN_CHALLENGE_USER_ID = "user_id"
        private const val COLUMN_CHALLENGE_DESCRIPTION = "description"

        private const val TABLE_PROFILE = "profile"
        private const val COLUMN_PROFILE_ADMIN_ID = "admin_id"
        private const val COLUMN_PROFILE_NAME = "name"
        private const val COLUMN_PROFILE_CODE = "code"
        private const val COLUMN_PROFILE_CHALLENGES = "challenges"
        private const val COLUMN_PROFILE_PLAYERS = "players"
        private const val COLUMN_PROFILE_DRAW = "draw"


    }
    private val CREATE_USER_TABLE = ("CREATE TABLE  " + TABLE_USER + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_USER_LOGIN + " TEXT,"
            + COLUMN_USER_PASSWORD + " TEXT, " +  COLUMN_USER_NAME + " TEXT" + ")")

    private val CREATE_PROFILE_TABLE = ("CREATE TABLE  " + TABLE_PROFILE + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_PROFILE_ADMIN_ID + " INTEGER,"
            + COLUMN_PROFILE_NAME + " TEXT, " + COLUMN_PROFILE_CODE + " TEXT, "
            + COLUMN_PROFILE_CHALLENGES + " TEXT, " + COLUMN_PROFILE_PLAYERS + " TEXT, "
            + COLUMN_PROFILE_DRAW + " TEXT " + ")")

    private val CREATE_CHALLENGE_TABLE = ("CREATE TABLE  " + TABLE_CHALLENGE + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_CHALLENGE_USER_ID + " INTEGER,"
            +  COLUMN_CHALLENGE_DESCRIPTION + " TEXT" + ")")

    private val DROP_USER_TABLE = "DROP TABLE IF EXISTS $TABLE_USER"
    private val DROP_PROFILE_TABLE = "DROP TABLE IF EXISTS $TABLE_PROFILE"
    private val DROP_CHALLENGE_TABLE = "DROP TABLE IF EXISTS $TABLE_CHALLENGE"

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_USER_TABLE)
        db.execSQL(CREATE_PROFILE_TABLE)
        db.execSQL(CREATE_CHALLENGE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, old: Int, new: Int) {
        db.execSQL(DROP_USER_TABLE)
        db.execSQL(DROP_PROFILE_TABLE)
        db.execSQL(DROP_CHALLENGE_TABLE)
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

    fun deleteProfile(name: String,adminId: Int) {
        val db = this.writableDatabase
        db.delete(TABLE_PROFILE, COLUMN_PROFILE_NAME + "= '$name' and " + COLUMN_PROFILE_ADMIN_ID + "= '$adminId' " ,null)
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

    fun getChallenges(userId: Int): MutableList<String>{
        val challenge: MutableList<String> = mutableListOf()
        val db = this.readableDatabase
        try {
            val query = db.rawQuery ("select * from challenge c where c.user_id = '$userId' or c.user_id = -1 ", null);
            if (!query.moveToFirst())
                return ArrayList()

            do {
                challenge.add(query.getString(2))
            } while (query.moveToNext())

        } finally {
            db.close()
            return challenge
        }
    }

    fun addChallenge(userId: Int, description: String) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_CHALLENGE_USER_ID, userId)
        values.put(COLUMN_CHALLENGE_DESCRIPTION, description)
        // Inserting Row
        db.insert(TABLE_CHALLENGE, null, values)
        db.close()
    }

    fun addProfile(adminId: Int, name: String, code: String, challenges: String, players: String, draw: String) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_PROFILE_ADMIN_ID, adminId)
        values.put(COLUMN_PROFILE_NAME, name)
        values.put(COLUMN_PROFILE_CODE, code)
        values.put(COLUMN_PROFILE_CHALLENGES, challenges)
        values.put(COLUMN_PROFILE_PLAYERS, players)
        values.put(COLUMN_PROFILE_DRAW, draw)
        // Inserting Row
        db.insert(TABLE_PROFILE, null, values)
        db.close()
    }

    fun updateProfile(profile: Profile) {
        deleteProfile(profile.name, profile.adminId)
        addProfile(profile.adminId, profile.name, profile.code, profile.challenges, profile.players, profile.draw)
    }

    fun getChallengeByDescription(userId: Int, description: String): MutableList<Int>{
        val challenge: MutableList<Int> = mutableListOf()
        val db = this.readableDatabase
        try {
            val query = db.rawQuery ("select * from challenge c where ( c.user_id = '$userId' or c.user_id = -1 ) and c.description = '$description' ", null);
            if (!query.moveToFirst())
                return ArrayList()

            do {
                challenge.add(query.getInt(0))
            } while (query.moveToNext())

        } finally {
            db.close()
            return challenge
        }
    }

    fun getProfiles(userId: Int): MutableList<String>{
        val challenge: MutableList<String> = mutableListOf()
        val db = this.readableDatabase
        try {
            val query = db.rawQuery ("select * from profile p where p.admin_id = '$userId' ", null);
            if (!query.moveToFirst())
                return ArrayList()

            do {
                challenge.add(query.getString(2))
            } while (query.moveToNext())

        } finally {
            db.close()
            return challenge
        }
    }
    fun getProfilesByName(userId: Int, name: String): MutableList<String>{
        val challenge: MutableList<String> = mutableListOf()
        val db = this.readableDatabase
        try {
            val query = db.rawQuery ("select * from profile p where p.admin_id = '$userId' and p.name = '$name' ", null);
            if (!query.moveToFirst())
                return ArrayList()

            do {
                challenge.add(query.getString(2))
            } while (query.moveToNext())

        } finally {
            db.close()
            return challenge
        }
    }

    fun getProfile(userId: Int, name: String): MutableList<Profile>{
        val profile: MutableList<Profile> = mutableListOf()
        val db = this.readableDatabase
        try {
            val query = db.rawQuery ("select * from profile p where p.admin_id = '$userId' and p.name = '$name' ", null);
            if (!query.moveToFirst())
                return ArrayList()

            do {
                profile.add(Profile(query.getInt(0),query.getInt(1),query.getString(2),query.getString(3), query.getString(4),query.getString(5), query.getString(6)))
            } while (query.moveToNext())

        } finally {
            db.close()
            return profile
        }
    }

    fun getProfileByCode(code: String): MutableList<Profile>{
        val profile: MutableList<Profile> = mutableListOf()
        val db = this.readableDatabase
        try {
            val query = db.rawQuery ("select * from profile p where p.code = '$code' ", null);
            if (!query.moveToFirst())
                return ArrayList()

            do {
                profile.add(Profile(query.getInt(0),query.getInt(1),query.getString(2),query.getString(3), query.getString(4),query.getString(5),query.getString(6)))
            } while (query.moveToNext())

        } finally {
            db.close()
            return profile
        }
    }

    fun getBooleanProfileByCode(code: String): Boolean {
        val profile: MutableList<Profile> = mutableListOf()
        val db = this.readableDatabase
        val query = db.rawQuery("select * from profile p where p.code = '$code' ", null);
        return query.moveToFirst()
    }
}

