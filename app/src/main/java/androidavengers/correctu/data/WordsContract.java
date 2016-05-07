package androidavengers.correctu.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by hp on 1/15/2016.
 */
public class WordsContract {

    public static final String CONTENT_AUTHORITY = "androidavengers.correctu.data";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_WORDS_DATA = "word_data";
    public static final String PATH_TYPE_OF_WORDS = "type_of_word_data";
    public static final String PATH_TYPE = "type";
    public static final String PATH_STATS = "statistics";


    public static final class WordEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_WORDS_DATA).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_WORDS_DATA;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_WORDS_DATA;

        public static final String TABLE_NAME = "words";

        public static final String COL_NAME = "name";

        public static final String COL_MEANING = "meaning";

        public static final String COL_PART_OF_SPEECH = "part_of_speech";

        public static final String COL_STATS_ID = "statistics_id";

        public static final String COL_TYPE_ID = "type_of_word_id";

        public static Uri buildWordURI(long id) {
            // This type is only suitable when attaching to _id parameter, not any other parameter
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildWordWithStatsURI (String stats_id){
            return CONTENT_URI.buildUpon().appendPath(stats_id).build();
        }

    }

    public static final class TypeEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_TYPE).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TYPE;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TYPE;

        public static final String TABLE_NAME = "type_of_word";

        public static final String COL_NAME = "name";

        public static Uri buildTypeURI(long id) {
            // This type is only suitable when attaching to _id parameter, not any other parameter
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

    }

    public static final class StatsEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_STATS).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_STATS;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_STATS;

        public static final String TABLE_NAME = "statistics";

        public static final String COL_PROGRESS = "progress";

        public static final String COL_TIMES_VIEWED = "times_viewed";

        public static final String COL_LAST_PRACTICED = "last_practiced";

        public static final String COL_TIMES_PRACTICED = "times_practiced";

        public static final String COL_WRONG_ANSWERS = "wrong_answers";

        public static final String COL_CORRECT_ANSWERS = "correct_answers";

        public static Uri buildWordURI(long id) {
            // This type is only suitable when attaching to _id parameter, not any other parameter
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

    }

}
