package com.example.android.yourhabitstracker;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.android.yourhabitstracker.data.HabitDbHelper;

import com.example.android.yourhabitstracker.data.HabitContract.HabitEntry;

public class MainActivity extends AppCompatActivity {

    /** Database helper that will provide us access to the database */
    private HabitDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // To access our database, we instantiate our subclass of SQLiteOpenHelper
        // and pass the context, which is the current activity.
        mDbHelper = new HabitDbHelper(this);

        // Insert dummy data
        mDbHelper.insertHabit();

        // Display data
        displayHabit();
    }

    /**
     * Temporary helper method to display information in the placeholder TextView about the state of
     * the habits database.
     */
    private void displayHabit() {

        Cursor cursor = mDbHelper.readAllHabits();

        // Find the placeholder TextView to show the data
        TextView displayView = (TextView) findViewById(R.id.placeholder_textview);

        try {
            // Create a header in the Text View that looks like this:
            // The habit table contains <number of rows in Cursor> habits.
            // _id - habit_name - frequency - comment - reminder
            displayView.setText("The habit table contains " + cursor.getCount() + " habit(s).\n\n");
            displayView.append(HabitEntry._ID + " - " +
                    HabitEntry.COLUMN_HABIT_NAME + " - " +
                    HabitEntry.COLUMN_HABIT_FREQUENCY + " - " +
                    HabitEntry.COLUMN_HABIT_COMMENT + " - " +
                    HabitEntry.COLUMN_HABIT_REMINDER + "\n");

            // Figure out the index of each column
            int idColumnIndex = cursor.getColumnIndex(HabitEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_HABIT_NAME);
            int frequencyColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_HABIT_FREQUENCY);
            int commentColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_HABIT_COMMENT);
            int reminderColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_HABIT_REMINDER);

            // In the while loop below, iterate through the rows of the cursor and display
            // the information from each column in this order.
            // Iterate through all the returned rows in the cursor
            while (cursor.moveToNext()) {
                // Use that index to extract the String or Int value of the word
                // at the current row the cursor is on.
                int currentID = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                int currentFrequency = cursor.getInt(frequencyColumnIndex);
                String currentComment = cursor.getString(commentColumnIndex);
                int currentReminder = cursor.getInt(reminderColumnIndex);

                // Display the values from each column of the current row in the cursor in the TextView
                displayView.append(("\n" + currentID + " - " +
                        currentName + " - " +
                        currentFrequency + " - " +
                        currentComment + " - " +
                        currentReminder));
            }
        } finally {
            // Close the cursor when it's done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }
    }
}
