package androidavengers.correctu.saved_words_screen;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidavengers.correctu.R;
import androidavengers.correctu.before_practice_screen.StartPracticeScreenViewPagerActivity;
import androidavengers.correctu.data.WordsContract;

/**
 * A placeholder fragment containing a simple view.
 */
public class SavedWordScreenFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int SAVED_WORDS_LOADER_ID = 111;
    private ListView mSavedWordsListView;
    private ListViewAdapter mListViewAdapter;

    public SavedWordScreenFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_saved_word_screen, container, false);
        mSavedWordsListView = (ListView) v.findViewById(R.id.saved_words_listview);
        mListViewAdapter = new ListViewAdapter(getActivity(), null);
        mSavedWordsListView.setAdapter(mListViewAdapter);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(SAVED_WORDS_LOADER_ID, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        return new CursorLoader(getActivity(), WordsContract.WordEntry.CONTENT_URI,
                null,
                WordsContract.WordEntry.TABLE_NAME + "." + WordsContract.WordEntry.COL_TYPE_ID + " = ? ",
                new String[]{"0"},
                null
        );
    }


    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mListViewAdapter.swapCursor(data);
    }


    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mListViewAdapter.swapCursor(null);
    }

    static class ListViewAdapter extends CursorAdapter {

        public ListViewAdapter(Context context, Cursor c) {
            super(context, c, 0);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            View v = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false);
            ViewHolder viewHolder = new ViewHolder(v);
            v.setTag(viewHolder);
            return v;
        }


        @Override
        public void bindView(View view, final Context context, final Cursor cursor) {
            ViewHolder v = (ViewHolder) view.getTag();
            final String data = cursor.getString(cursor.getColumnIndex(WordsContract.WordEntry.COL_NAME));
            v.word_type.setText(data);
            v.word_type.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, StartPracticeScreenViewPagerActivity.class);
                    intent.putExtra(StartPracticeScreenViewPagerActivity.WORD_TYPE_EXTRA, 0);
                    intent.putExtra(StartPracticeScreenViewPagerActivity.CURRENT_POSITION_EXTRA, cursor.getPosition());
                    context.startActivity(intent);
                }
            });

        }

        static class ViewHolder {
            TextView word_type;

            public ViewHolder(View v) {
                word_type = (TextView) v.findViewById(android.R.id.text1);

            }
        }
    }

}
