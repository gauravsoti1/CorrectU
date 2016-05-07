package androidavengers.correctu.before_practice_screen;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

import androidavengers.correctu.R;
import androidavengers.correctu.data.WordsContract;
import androidavengers.correctu.enhance_time_screen.EnhanceTimeScreenActivity;

public class StartPracticeScreenViewPagerActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final String TAG = StartPracticeScreenViewPagerActivity.class.getSimpleName();
    public static final String CURRENT_POSITION_EXTRA = "current_position";
    public static final String WORD_TYPE_EXTRA = "word_type";
    private static final int SAVED_WORDS_LOADER_ID = 111;
    private MyCursorPagerAdapter mCursorPagerAdapter;
    private ViewPager mViewPager;
    private int mCurrentPosition;
    private int mWordType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_practice_screen_view_pager);

        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/
        getSupportLoaderManager().initLoader(SAVED_WORDS_LOADER_ID, null, this);
        mCursorPagerAdapter = new MyCursorPagerAdapter(this, null);
        mCurrentPosition = getIntent().getIntExtra(CURRENT_POSITION_EXTRA, 0);
        mWordType = getIntent().getIntExtra(WORD_TYPE_EXTRA, 0);
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mCursorPagerAdapter);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, WordsContract.WordEntry.CONTENT_URI,
                null,
                WordsContract.WordEntry.TABLE_NAME + "." + WordsContract.WordEntry.COL_TYPE_ID + " = ? ",
                new String[]{Integer.toString(mWordType)},
                null
        );
    }


    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        mCursorPagerAdapter.swapCursor(data);
        mViewPager.setCurrentItem(mCurrentPosition);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mCursorPagerAdapter.swapCursor(null);

    }


    public static class MyCursorPagerAdapter extends PagerAdapter implements View.OnClickListener {

        private final Context context;
        int[] colorsArray;
        private Cursor cursor;
        private LayoutInflater inflater;
        private Random mRan;

        public MyCursorPagerAdapter(Context context, Cursor cursor) {

            Log.d(StartPracticeScreenViewPagerActivity.TAG, "MyCursorPagerAdapter.onCreate()");

            this.cursor = cursor;
            this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            this.context = context;
            initializeColors();
        }

        public void initializeColors() {
            colorsArray = context.getResources().getIntArray(R.array.background_colors);
        }

        public void swapCursor(Cursor cursor) {
            //if(cursor != null) {

            Log.d(StartPracticeScreenViewPagerActivity.TAG, "swapCursor().");

            this.cursor = cursor;
            notifyDataSetChanged();
            //}
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView((RelativeLayout) object);
        }

        @Override
        public int getCount() {
            if (cursor == null) {
                return 0;
            } else {
                return cursor.getCount();
            }
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            TextView wordLabel;
            Button startButton;
            Button nextButton;
            Button prevButton;
            View layout = inflater.inflate(R.layout.fragment_start_practice_screen_view_pager, container, false);
            layout.setBackgroundColor(colorsArray[getRandomColor()]);
            wordLabel = (TextView) layout.findViewById(R.id.word_label);
         //   wordLabel.setText(cursor.getString(cursor.getColumnIndex(WordsContract.WordEntry.COL_NAME)));
            startButton = (Button) layout.findViewById(R.id.start_button);
            startButton.setOnClickListener(this);
            prevButton = (Button) layout.findViewById(R.id.previous_btn);
            prevButton.setTag(position);
            prevButton.setOnClickListener(this);
            nextButton = (Button) layout.findViewById(R.id.next_btn);
            nextButton.setTag(position);
            nextButton.setOnClickListener(this);
            ((ViewPager) container).addView(layout);
            return layout;
        }


        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((RelativeLayout) object);
        }

        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.start_button:
                    context.startActivity(new Intent(context, EnhanceTimeScreenActivity.class));
                    //TODO implement
                    break;
                case R.id.previous_btn:
                    ((StartPracticeScreenViewPagerActivity) context).mViewPager.setCurrentItem((int) v.getTag() - 1, true);
                    break;
                case R.id.next_btn:
                    ((StartPracticeScreenViewPagerActivity) context).mViewPager.setCurrentItem((int) v.getTag() + 1, true);
                    break;

            }
        }

        public int getRandomColor() {
            return (int) (Math.random() * colorsArray.length);

        }


    }
}