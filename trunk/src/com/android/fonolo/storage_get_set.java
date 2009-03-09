/***
 * 
 * @author Craig Gardner, John St. John, Abdul Binrsheed
 * Last update March 2009
 * 
 * The Storage get set class has methods that deal with the sqlite data base.
 * The DB is used to store the user login information, a list of companies favorites list that created 
 * by the user, and to store the value of the eula statues. We used the Google note DB tutorial to 
 * implement some of the class functionality.  
 */
 

package com.android.fonolo4android;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class storage_get_set {
       
        //set the columns name for the user information table
    public static final String KEY_UNAME = "user";
    public static final String KEY_PASS = "password";
    public static final String KEY_PHONE= "phone";
   
    //set the columns name for the favorites list table
    public static final String KEY_ID = "c_id";
    public static final String KEY_NAME = "name";
   
    //set the columns name for the eula table
    public static final String KEY_EULA_ID = "junk";
    public static final String KEY_EULA = "value";

    private static final String TAG = "storage_get_set";
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;
   
    /**
     * Database creation sql statement
     */
    private static final String LOGIN_DB_CREATE =
            "create table login (user text primary key, "
                    + "password text not null, phone text not null);";
    private static final String FAVS_DB_CREATE =
        "create table favorites (c_id text primary key, "
        + "name text not null);";
    private static final String EULA_DB_CREATE =
        "create table eula (junk integer primary key, "
        + "value integer not null);";
    
    // Set the DB schema
    private static final String DATABASE_NAME = "data";
    private static final String LOGIN_DB_TABLE = "login";
    private static final String EULA_DB_TABLE = "eula";
    private static final String FAVORITES_DB_TABLE = "favorites";
    private static final int DATABASE_VERSION = 11;

    private final Context mCtx;
    
    //Initialize the DB.
    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        //Set an initialize value eula table
        public void onCreate(SQLiteDatabase db) {
                        db.execSQL(LOGIN_DB_CREATE);
                        db.execSQL(FAVS_DB_CREATE);
                        db.execSQL(EULA_DB_CREATE);
                        try{
                        String initialize = "insert into eula values (0,0);";
                        db.execSQL(initialize);
                        }catch(Exception e){
                                try{
                                        String initialize = "update eula set value=0 where junk=0;";
                                        db.execSQL(initialize);
                                }catch(Exception e2){
                                        //Never gets to here, only to throw a try catch.
                                }
                        }
           
        }

        @Override
        // Recreate the DB when the DB version is changed to get the new updates.
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS favorites");
            db.execSQL("DROP TABLE IF EXISTS login");
            db.execSQL("DROP TABLE IF EXISTS eula");
            onCreate(db);
        }
    }

    /**
     * Constructor - takes the context to allow the database to be
     * opened/created
     *
     * @param ctx the Context within which to work
     */
    //
    public storage_get_set(Context ctx) {
        this.mCtx = ctx;
    }

    /**
     * Open the DB. If it cannot be opened, try to create a new
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
   // Close the DB.
    public void close() {
        mDbHelper.close();
    }


    /**
     * Store the user login info using the info provided. If entry is
     * successfully stored return the rowId for that note, otherwise return
     * a -1 to indicate failure.
     *
     */
    public long createLogin(String user, String password, String phone) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_UNAME, user);
        initialValues.put(KEY_PASS, password);
        initialValues.put(KEY_PHONE, phone);

        return mDb.insert(LOGIN_DB_TABLE, null, initialValues);
    }
   
    /**
     * Store a new favorite using the company name and id provided. If entry is
     * successfully stored return the rowId for that note, otherwise return
     * a -1 to indicate failure.
     *
     */
    public long createFavorites(String id, String name) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_ID, id);
        initialValues.put(KEY_NAME, name);

        return mDb.insert(FAVORITES_DB_TABLE, null, initialValues);
    }
    
   // Check if the favorite table has records.
    public boolean checkFavorite(String id) throws SQLException {

        Cursor mCursor =

                mDb.query(true, FAVORITES_DB_TABLE, new String[] {KEY_ID,
                        KEY_NAME}, KEY_ID + "='" + id + "'", null,
                        null, null, null, null);
        if (mCursor != null && mCursor.getCount() != 0) {
            return true;
        }else{
                return false;
        }

    }
    
    // Change the eula record when the user agree to the agreement.
    public boolean setEulaTrue() {
        ContentValues args = new ContentValues();
        args.put(KEY_EULA_ID, 0);
        args.put(KEY_EULA, 1);

        return mDb.update(EULA_DB_TABLE, args, KEY_EULA_ID + "=" + "0", null) > 0;
    }
    // have the ability to change the eula value if needed.(currently not used)
    public boolean setEulaFalse(){
        ContentValues args = new ContentValues();
        args.put(KEY_EULA_ID, 0);
        args.put(KEY_EULA, 0);


        return mDb.update(EULA_DB_TABLE, args, KEY_EULA_ID + "=" + "0", null) > 0;
    }
    /**
     * Delete the login info(we delete all from the table since we only allow for one user login info)
     *
     * @return true if deleted, false otherwise
     */
    public boolean deleteLogin() {
    	return mDb.delete(LOGIN_DB_TABLE, null, null) > 0;
    }
   
    /**
     * Delete from favorites company with the given Id
     *
     * @return true if deleted, false otherwise
     */
    public boolean deleteFavorites(String id) {

        return mDb.delete(FAVORITES_DB_TABLE, KEY_ID + "='" + id + "'", null) > 0;
    }

    /**
     * Return a Cursor over the list of all favorites in the database
     *
     * @return Cursor over all favorites
     */
    public Cursor fetchAllFavorites() {

        return mDb.query(FAVORITES_DB_TABLE, new String[] {KEY_ID, KEY_NAME}, null, null, null, null, KEY_NAME);
    }
   
    /**
     * Return a Cursor over the user login info in the database
     *
     * @return Cursor over login info
     */
    public Cursor fetchLogin() {
       
        return mDb.query(LOGIN_DB_TABLE, new String[] {KEY_UNAME, KEY_PASS, KEY_PHONE}, null, null, null, null, null);
    }
    /**
     * Return a Cursor over the eula status in the database
     *
     * @return Cursor over eula value
     */
    public Cursor fetchEula() {
       
        return mDb.query(EULA_DB_TABLE, new String[] {KEY_EULA_ID, KEY_EULA}, null, null, null, null, null);
    }

    /**
     * Update the user login info using the details provided. The note to be updated is
     * specified using the username, and it is altered to use the new values passed in
     *
     * @return true if the record was successfully updated, false otherwise
     */
    public boolean updateLogin(String user, String pass, String phone) {
        ContentValues args = new ContentValues();
        args.put(KEY_UNAME, user);
        args.put(KEY_PASS, pass);
        args.put(KEY_PHONE, phone);

        return mDb.update(LOGIN_DB_TABLE, args, KEY_UNAME + "=" + user, null) > 0;
    }
}

