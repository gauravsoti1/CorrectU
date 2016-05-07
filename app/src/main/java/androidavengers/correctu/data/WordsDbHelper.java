package androidavengers.correctu.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by hp on 1/15/2016.
 */
public class WordsDbHelper extends SQLiteOpenHelper {

    private static final String TAG = WordsDbHelper.class.getSimpleName();

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "words.db";

    private static final String AUTOINCREMENT = " AUTOINCREMENT ";
    private static final String PRIMARY_KEY = " PRIMARY KEY ";
    private static final String FOREIGN_KEY = " FOREIGN KEY ";
    private static final String sINTEGER = " integer ";
    private static final String sVARCHAR = " varchar ";
    private static final String sCHAR = " char ";
    private static final String CREATE_TABLE = " CREATE TABLE ";
    private static final String REFERENCES = " REFERENCES ";
    private static final String OPENING_PARENTHESIS = " ( ";
    private static final String CLOSING_PARENTHESIS = " ) ";
    private static final String COMMA = " , ";
    private static final String SEMICOLON = " ; ";
    private static final String DROP_TABLE_COMMAND = "DROP TABLE IF EXISTS ";


    public WordsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        Log.d(TAG, "inside on create");

        final String SQL_CREATE_TYPE_TABLE = CREATE_TABLE +
                WordsContract.TypeEntry.TABLE_NAME + OPENING_PARENTHESIS +
                WordsContract.TypeEntry._ID + sINTEGER + PRIMARY_KEY + AUTOINCREMENT + COMMA +
                WordsContract.TypeEntry.COL_NAME + sCHAR +
                CLOSING_PARENTHESIS + SEMICOLON;


        final String SQL_CREATE_WORDS_TABLE = CREATE_TABLE +
                WordsContract.WordEntry.TABLE_NAME + OPENING_PARENTHESIS +
                WordsContract.WordEntry._ID + sINTEGER + PRIMARY_KEY + AUTOINCREMENT + COMMA +
                WordsContract.WordEntry.COL_NAME + sVARCHAR + COMMA +
                WordsContract.WordEntry.COL_MEANING + sVARCHAR + COMMA +
                WordsContract.WordEntry.COL_PART_OF_SPEECH + sCHAR + COMMA +
                WordsContract.WordEntry.COL_STATS_ID + sINTEGER + COMMA +
                WordsContract.WordEntry.COL_TYPE_ID + sINTEGER + COMMA +
                WordsContract.StatsEntry.COL_PROGRESS + sINTEGER + COMMA +
                WordsContract.StatsEntry.COL_TIMES_VIEWED + sINTEGER + COMMA +
                WordsContract.StatsEntry.COL_LAST_PRACTICED + sINTEGER + COMMA +
                WordsContract.StatsEntry.COL_TIMES_PRACTICED + sINTEGER + COMMA +
                WordsContract.StatsEntry.COL_WRONG_ANSWERS + sINTEGER + COMMA +
                WordsContract.StatsEntry.COL_CORRECT_ANSWERS + sINTEGER + COMMA +
                FOREIGN_KEY + OPENING_PARENTHESIS + WordsContract.WordEntry.COL_TYPE_ID + CLOSING_PARENTHESIS +
                REFERENCES + WordsContract.TypeEntry.TABLE_NAME + OPENING_PARENTHESIS + WordsContract.TypeEntry._ID +
                CLOSING_PARENTHESIS + CLOSING_PARENTHESIS + SEMICOLON;

        /*final String SQL_CREATE_STATS_TABLE = CREATE_TABLE +
                WordsContract.StatsEntry.TABLE_NAME + OPENING_PARENTHESIS +
                WordsContract.StatsEntry._ID + sINTEGER + COMMA +
                WordsContract.StatsEntry.COL_PROGRESS + sINTEGER + COMMA +
                WordsContract.StatsEntry.COL_TIMES_VIEWED + sINTEGER + COMMA +
                WordsContract.StatsEntry.COL_LAST_PRACTICED + sINTEGER + COMMA +
                WordsContract.StatsEntry.COL_TIMES_PRACTICED + sINTEGER + COMMA +
                WordsContract.StatsEntry.COL_WRONG_ANSWERS + sINTEGER + COMMA +
                WordsContract.StatsEntry.COL_CORRECT_ANSWERS + sINTEGER + COMMA +
                FOREIGN_KEY + OPENING_PARENTHESIS + WordsContract.StatsEntry._ID + CLOSING_PARENTHESIS +
                REFERENCES + WordsContract.WordEntry.TABLE_NAME +
                OPENING_PARENTHESIS + WordsContract.WordEntry._ID + CLOSING_PARENTHESIS +
                CLOSING_PARENTHESIS + SEMICOLON;*/


        db.execSQL(SQL_CREATE_TYPE_TABLE);
        db.execSQL(SQL_CREATE_WORDS_TABLE);
        //db.execSQL(SQL_CREATE_STATS_TABLE);

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL(DROP_TABLE_COMMAND + WordsContract.StatsEntry.TABLE_NAME);
        db.execSQL(DROP_TABLE_COMMAND + WordsContract.WordEntry.TABLE_NAME);
        db.execSQL(DROP_TABLE_COMMAND + WordsContract.TypeEntry.TABLE_NAME);

        onCreate(db);

    }


}
