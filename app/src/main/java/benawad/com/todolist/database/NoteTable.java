package benawad.com.todolist.database;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by benawad on 4/1/15.
 */
public class NoteTable {

    public static final String TABLE_NOTE = "note";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_ITEMS = "items";
    public static final String COLUMN_SLASHED = "slashed";
    public static final String COLUMN_NOTE_TITLE = "noteTitle";

    // Database creation SQL statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_NOTE
            + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_SLASHED + " text not null, "
            + COLUMN_NOTE_TITLE + " text not null, "
            + COLUMN_ITEMS
            + " text not null"
            + ");";

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion,
                                 int newVersion) {
        Log.w(NoteTable.class.getName(), "Upgrading database from version "
                + oldVersion + " to " + newVersion
                + ", which will destroy all old data");
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTE);
        onCreate(database);
    }

}
