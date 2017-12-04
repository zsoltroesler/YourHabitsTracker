package com.example.android.yourhabitstracker.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.android.yourhabitstracker.data.HabitContract.HabitEntry;

/**
 * Created by Zsolt on 2017. 10. 31..
 */

public class HabitDbHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = HabitDbHelper.class.getSimpleName();

    /** Database name */
    private static final String DATABASE_NAME = "habits.db";

    /** Database version */
    private static final int DATABASE_VERSION = 1;

    /** Our database */
    private SQLiteDatabase db;

    /**
     * Constructs a new instance of {@link HabitDbHelper}.
     * @param context of the app
     */
    public HabitDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /** This is called when the database is created for the first time. */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create a String that contains the SQL statement to create the pets table
        String SQL_CREATE_HABITS_TABLE =  "CREATE TABLE " + HabitContract.HabitEntry.TABLE_NAME + " ("
                + HabitEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + HabitEntry.COLUMN_HABIT_NAME + " TEXT NOT NULL, "
                + HabitEntry.COLUMN_HABIT_FREQUENCY + " INTEGER NOT NULL DEFAULT 0, "
                + HabitEntry.COLUMN_HABIT_COMMENT + " TEXT, "
                + HabitEntry.COLUMN_HABIT_REMINDER + " INTEGER NOT NULL DEFAULT 0);";

        // Execute the SQL statement
        db.execSQL(SQL_CREATE_HABITS_TABLE);
    }

    /** This is called when the database is upgraded according to changes of database schema */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String SQL_DELETE_TABLE = "DROP TABLE IF EXISTS " + HabitEntry.TABLE_NAME;
        db.execSQL(SQL_DELETE_TABLE);
        onCreate(db);
    }

    /**
     * Helper method to insert hardcoded habit data into the database. For debugging purposes only.
     */
    public void insertHabit() {
        // Gets the database in write mode
        db = getWritableDatabase();

        // Create a ContentValues object where column names are the keys,
        // and habit attributes from dummy data are the values.
        ContentValues values = new ContentValues();
        values.put(HabitEntry.COLUMN_HABIT_NAME, "Mountain biking");
        values.put(HabitEntry.COLUMN_HABIT_FREQUENCY, HabitEntry.FREQUENCY_WEEKLY);
        values.put(HabitEntry.COLUMN_HABIT_COMMENT, "Shredding the trails at least once per week.");
        values.put(HabitEntry.COLUMN_HABIT_REMINDER, HabitEntry.REMINDER_TRUE);

        // Insert a new row for mountain biking in the database, returning the ID of that new row.
        // The first argument for db.insert() is the habits table name.
        // The second argument provides the name of a column in which the framework
        // can insert NULL in the event that the ContentValues is empty (if
        // this is set to "null", then the framework will not insert a row when
        // there are no values).
        // The third argument is the ContentValues object containing the info for habit.
        long newRowId = db.insert(HabitEntry.TABLE_NAME, null, values);

        // Check log, whether new row created
        Log.v("MainActivity", "New row ID " + newRowId);
    }

    // Perform a query on the habit table
    public Cursor readAllHabits() {
        // Open the database to read from it
        db = getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                HabitEntry._ID,
                HabitEntry.COLUMN_HABIT_NAME,
                HabitEntry.COLUMN_HABIT_FREQUENCY,
                HabitEntry.COLUMN_HABIT_COMMENT,
                HabitEntry.COLUMN_HABIT_REMINDER
        };

        // Perform a query on the habit table
        return db.query(
                HabitEntry.TABLE_NAME,      // The table to query
                projection,                 // The columns to return
                null,              // The columns for the WHERE clause
                null,           // The columns for the WHERE clause
                null,              // Don't group the rows
                null,               // Don't filter by row groups
                null                // The sort order
        );
    }
}
