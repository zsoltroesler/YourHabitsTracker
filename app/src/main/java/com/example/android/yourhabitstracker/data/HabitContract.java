package com.example.android.yourhabitstracker.data;

/**
 * Created by Zsolt on 2017. 10. 31..
 */


import android.provider.BaseColumns;

/**
 * Database schema
 */
public class HabitContract {

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private HabitContract() {
    }

    /**
     * Inner class that defines constant values for the habits database table.
     * Each entry in the table represents a single habit.
     */
    public static final class HabitEntry implements BaseColumns {

        /**
         * Name of database table for habits
         */
        public final static String TABLE_NAME = "habits";

        /**
         * Unique ID number for a single habit(only for use in the database table).
         * Type: INTEGER
         */
        public final static String _ID = BaseColumns._ID;

        /**
         * Name of the habit.
         * Type: TEXT
         */
        public final static String COLUMN_HABIT_NAME = "habit_name";

        /**
         * Frequencyof the habit
         * The only possible values are {@link #FREQUENCY_DAILY}, {@link #FREQUENCY_WEEKLY},
         * {@link #FREQUENCY_MONTHLY} or {@link #FREQUENCY_YEARLY}
         * Type: INTEGER
         */
        public final static String COLUMN_HABIT_FREQUENCY = "frequency";

        /**
         * Comment of the habit.
         * Type: TEXT
         */
        public final static String COLUMN_HABIT_COMMENT = "comment";

        /**
         * Whether or not a reminder for the habit.
         * The only possible values are {@link #REMINDER_FALSE} or {@link #REMINDER_TRUE},
         * Type: INTEGER
         */
        public final static String COLUMN_HABIT_REMINDER = "reminder";

        /**
         * Possible values for the frequency of the habit.
         */
        public static final int FREQUENCY_DAILY = 0;
        public static final int FREQUENCY_WEEKLY = 1;
        public static final int FREQUENCY_MONTHLY = 2;
        public static final int FREQUENCY_YEARLY = 3;

        /**
         * Possible values for the reminder. SQLite does not have a separate Boolean storage class.
         * Instead, Boolean values are stored as integers
         */
        public static final int REMINDER_FALSE = 0;
        public static final int REMINDER_TRUE = 1;

    }
}
