package androidavengers.correctu.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by android avenger on 1/15/2016.
 */
public class WordsProvider extends ContentProvider {

    static final int WORDS = 100;
    static final int WORDS_WITH_STATS = 101;
    static final int TYPE_OF_WORDS = 102;


    // ***************** URIs **********************//
    static final int STATS = 301;
    static final int TYPE = 200;
    private static final String TAG = WordsProvider.class.getSimpleName();
    /*private static final SQLiteQueryBuilder sWordsWithStats;*/
    private static UriMatcher sUriMatcher = buildUriMatcher();


    // ********************************************//

    /*static {
        sWordsWithStats = new SQLiteQueryBuilder();

        sWordsWithStats.setTables(
                WordsContract.WordEntry.TABLE_NAME + " INNER JOIN " +
                        WordsContract.StatsEntry.TABLE_NAME +
                        " ON " + WordsContract.WordEntry.TABLE_NAME +
                        "." + WordsContract.WordEntry.COL_STATS_ID +
                        " = " + WordsContract.StatsEntry.TABLE_NAME +
                        "." + WordsContract.StatsEntry._ID

        );
    }*/

    private WordsDbHelper mDbHelper;

    static UriMatcher buildUriMatcher() {
        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = WordsContract.CONTENT_AUTHORITY;
        sUriMatcher.addURI(authority, WordsContract.PATH_WORDS_DATA, WORDS);
        //sUriMatcher.addURI(authority, WordsContract.PATH_WORDS_DATA + "/*", WORDS_WITH_STATS);
        //sUriMatcher.addURI(authority, WordsContract.PATH_TYPE_OF_WORDS, TYPE_OF_WORDS);
        sUriMatcher.addURI(authority, WordsContract.PATH_TYPE, TYPE);

        return sUriMatcher;

    }

    @Override
    public boolean onCreate() {
        Log.d(TAG, "Inside OnCreate");
        mDbHelper = new WordsDbHelper(getContext());

        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor retCursor;
        switch (sUriMatcher.match(uri)) {
            case WORDS: {
                Log.d(TAG, "Inside Query, type_of words ");
                retCursor = mDbHelper.getReadableDatabase().query(WordsContract.WordEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case TYPE: {
                Log.d(TAG, "Inside Query, type ");
                retCursor = mDbHelper.getReadableDatabase().query(WordsContract.TypeEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            /*case WORDS_WITH_STATS: {
                Log.d(TAG, "Inside Query, words_with_stats ");
                retCursor = sWordsWithStats.query(mDbHelper.getReadableDatabase(),
                        projection, selection, selectionArgs, null, null, sortOrder
                );
                break;
            }*/

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }


    @Nullable
    @Override
    public String getType(Uri uri) {

        final int match = sUriMatcher.match(uri);

        switch (match) {
            case WORDS: {
                return WordsContract.WordEntry.CONTENT_TYPE;
            }
            case TYPE: {
                Log.d(TAG, "Inside getType, type ");
                return WordsContract.TypeEntry.CONTENT_TYPE;
            }
            /*case WORDS_WITH_STATS: {
                Log.d(TAG, "Inside getType, words ");
                return WordsContract.WordEntry.CONTENT_TYPE;
            }*/
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }


    }


    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mDbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;
        switch (match) {
            case TYPE: {
                long _id = db.insert(WordsContract.TypeEntry.TABLE_NAME, null, values);
                if (_id > 0)
                    returnUri = WordsContract.TypeEntry.buildTypeURI(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }case WORDS: {
                long _id = db.insert(WordsContract.WordEntry.TABLE_NAME, null, values);
                if (_id > 0)
                    returnUri = WordsContract.WordEntry.buildWordURI(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        //Use the passed in uri. return uri will not notify cursor of change
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        final SQLiteDatabase db = mDbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);

        switch (match) {
            case TYPE: {
                db.beginTransaction();
                int returnCount = 0;
                try {
                    for (ContentValues value : values) {
                        long _id = db.insert(WordsContract.TypeEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;

            }

            default:
                return super.bulkInsert(uri, values);


        }


    }


    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }


    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
