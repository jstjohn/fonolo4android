/*
 * Copyright (C) 2008 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.android.fonolo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Simple notes database access helper class. Defines the basic CRUD operations
 * for the notepad example, and gives the ability to list all notes as well as
 * retrieve or modify a specific note.
 * 
 * This has been improved from the first version of this tutorial through the
 * addition of better error handling and also using returning a Cursor instead
 * of using a collection of inner classes (which is less scalable and not
 * recommended).
 */
public class storage_get_set {
	
	//stuff for our user information table
    public static final String KEY_UNAME = "user";
    public static final String KEY_PASS = "password";
    public static final String KEY_PHONE= "phone";
    
    //stuff for our favorites list table
    public static final String KEY_ID = "_id";
    public static final String KEY_NAME = "name";

    private static final String TAG = "storage_get_set";
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;
    
    /**
     * Database creation sql statement
     */
    private static final String LOGIN_DB_CREATE =
            "create table login (user varchar(40) primary key, "
                    + "password varchar(40) not null, phone varchar(14) not null);";
    private static final String FAVS_DB_CREATE = 
    	"create table login (_id varchar(34) primary key, "
        + "name varchar(40) not null);";
    private static final String DATABASE_NAME = "data";
    private static final String LOGIN_DB_TABLE = "login";
    private static final String FAVORITES_DB_TABLE = "favorites";
    private static final int DATABASE_VERSION = 2;

    private final Context mCtx;

    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL(LOGIN_DB_CREATE);
            db.execSQL(FAVS_DB_CREATE);
            
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS favorites");
            db.execSQL("DROP TABLE IF EXISTS login");
            onCreate(db);
        }
    }

    /**
     * Constructor - takes the context to allow the database to be
     * opened/created
     * 
     * @param ctx the Context within which to work
     */
    public storage_get_set(Context ctx) {
        this.mCtx = ctx;
    }

    /**
     * Open the notes database. If it cannot be opened, try to create a new
     * instance of the database. If it cannot be created, throw an exception to
     * signal the failure
     * 
     * @return this (self reference, allowing this to be chained in an
     *         initialization call)
     * @throws SQLException if the database could be neither opened or created
     */
    public storage_get_set open() throws SQLException {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }
    
    public void close() {
        mDbHelper.close();
    }


    /**
     * Create a new note using the title and body provided. If the note is
     * successfully created return the new rowId for that note, otherwise return
     * a -1 to indicate failure.
     * 
     * @param title the title of the note
     * @param body the body of the note
     * @return rowId or -1 if failed
     */
    public long createLogin(String user, String password, String phone) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_UNAME, user);
        initialValues.put(KEY_PASS, password);
        initialValues.put(KEY_PHONE, phone);

        return mDb.insert(LOGIN_DB_TABLE, null, initialValues);
    }
    
    /**
     * Create a new note using the title and body provided. If the note is
     * successfully created return the new rowId for that note, otherwise return
     * a -1 to indicate failure.
     * 
     * @param title the title of the note
     * @param body the body of the note
     * @return rowId or -1 if failed
     */
    public long createFavorites(String _id, String name) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_ID, _id);
        initialValues.put(KEY_NAME, name);

        return mDb.insert(FAVORITES_DB_TABLE, null, initialValues);
    }

    /**
     * Delete the note with the given rowId
     * 
     * @param user username of person to delete
     * @return true if deleted, false otherwise
     */
    public boolean deleteLogin(String user) {

        return mDb.delete(LOGIN_DB_TABLE, KEY_UNAME + "=" + user, null) > 0;
    }
    
    /**
     * Delete the note with the given rowId
     * 
     * @param user username of person to delete
     * @return true if deleted, false otherwise
     */
    public boolean deleteFavorites(String _id) {

        return mDb.delete(FAVORITES_DB_TABLE, KEY_ID + "=" + _id, null) > 0;
    }

    /**
     * Return a Cursor over the list of all favorites in the database
     * 
     * @return Cursor over all notes
     */
    public Cursor fetchAllFavorites() {
    	
        return mDb.query(FAVORITES_DB_TABLE, new String[] {KEY_ID, KEY_NAME}, null, null, null, null, null);
    }
    
    /**
     * Return a Cursor over the list of all favorites in the database
     * 
     * @return Cursor over all notes
     */
    public Cursor fetchLogin() {
    	
        return mDb.query(LOGIN_DB_TABLE, new String[] {KEY_UNAME, KEY_PASS, KEY_PHONE}, null, null, null, null, null);
    }
    

    /**
     * Update the note using the details provided. The note to be updated is
     * specified using the rowId, and it is altered to use the title and body
     * values passed in
     * 
     * @param rowId id of note to update
     * @param title value to set note title to
     * @param body value to set note body to
     * @return true if the note was successfully updated, false otherwise
     */
    public boolean updateLogin(String user, String pass, String phone) {
        ContentValues args = new ContentValues();
        args.put(KEY_UNAME, user);
        args.put(KEY_PASS, pass);
        args.put(KEY_PHONE, phone);

        return mDb.update(LOGIN_DB_TABLE, args, KEY_UNAME + "=" + user, null) > 0;
    }
}
