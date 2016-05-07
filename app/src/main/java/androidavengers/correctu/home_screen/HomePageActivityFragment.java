package androidavengers.correctu.home_screen;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidavengers.correctu.R;
import androidavengers.correctu.data.WordsContract;
import androidavengers.correctu.saved_words_screen.SavedWordScreen;
import androidavengers.correctu.storage_room_screen.StorageRoomActivity;
import androidavengers.correctu.utility.CommonConstants;
import androidavengers.correctu.utility.Utility;

/**
 * A placeholder fragment containing a simple view.
 */
public class HomePageActivityFragment extends Fragment implements HomeScreenView, View.OnClickListener, LoaderManager.LoaderCallbacks<Cursor> {

    public static final String TAG = HomePageActivityFragment.class.getSimpleName();
    private static final int WORD_TYPE_LOADER_ID = 200;
    private ListView mHomeScreenOptionsListview;
    private HomeScreenPresenter mPresenter;
    private Button mActivateInstructionBtn;
    private Button mLaterInstructionBtn;
    private CardView mInstructionCardViewContainer;
    private ListViewAdapter mListViewAdapter;
    private SharedPreferences mPrefs;

    public HomePageActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home_page, container, false);
        mHomeScreenOptionsListview = (ListView) v.findViewById(R.id.home_screen_options);
        mActivateInstructionBtn = (Button) v.findViewById(R.id.instruction_activate_btn);
        mActivateInstructionBtn.setOnClickListener(this);
        mLaterInstructionBtn = (Button) v.findViewById(R.id.instruction_later_btn);
        mLaterInstructionBtn.setOnClickListener(this);
        mInstructionCardViewContainer = (CardView) v.findViewById(R.id.activate_persistent_notification_cardview);
        if (Utility.checkWhetherDisplayedNotificationSettingCardView(getActivity())) {
            mInstructionCardViewContainer.setVisibility(View.GONE);
        }
        mPresenter = new HomeScreenPresenter(this);
        Log.d(TAG, " Inside onCreateView");
        // getSynonyms();
        mListViewAdapter = new ListViewAdapter(getActivity(), null);
        mHomeScreenOptionsListview.setAdapter(mListViewAdapter);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(WORD_TYPE_LOADER_ID, null, this);
        mHomeScreenOptionsListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Type = 1) Saved Words, 2) Commonly Misspelled Words
                final int typeOfWord = 0;
                Intent intent = new Intent(getActivity(), SavedWordScreen.class);
                intent.putExtra(CommonConstants.TYPE_KEY,typeOfWord);
                startActivity(intent);
                //mPresenter.onItemClicked(typeOfWord);
            }
        });
    }

    @Override
    public void startDisplayWordsActivity() {
        //TODO: Replace Object class with displayWordsActivity class
        final Intent displayWordsActivityIntent = new Intent(getActivity(), Object.class);
        startActivity(displayWordsActivityIntent);
    }

    void displayPersistentNotification() {

        Intent intent = new Intent(getActivity(), StorageRoomActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(),
                CommonConstants.OPEN_STORAGE_SCREEN_NOTIFICATION_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity().getApplicationContext());
        builder.setContentTitle("CorrectU");
        builder.setContentText("Instant Save");
        builder.setColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
        builder.setNumber(101);
        builder.setContentIntent(pendingIntent);
        builder.setTicker("Instant Save");
        builder.setSmallIcon(R.mipmap.ic_launcher);
        //builder.setLargeIcon(R.drawable.ic_timer);
        builder.setAutoCancel(false);
        builder.setPriority(0);
        builder.setOngoing(true);
        NotificationManager mNotifyMgr =
                (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        mNotifyMgr.notify(CommonConstants.OPEN_STORAGE_SCREEN_NOTIFICATION_ID, builder.build());


    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();

        switch (id) {

            case R.id.instruction_activate_btn:
                displayPersistentNotification();
                mInstructionCardViewContainer.setVisibility(View.GONE);
                if(mPrefs == null){
                    mPrefs = Utility.getSharedPreferences(getActivity());
                }
                Utility.updateNotificationSetting(mPrefs, true);
                Utility.updateDisplayInstantSave(getActivity(),mPrefs,true);

                break;

            case R.id.instruction_later_btn:
                mInstructionCardViewContainer.setVisibility(View.GONE);
                if(mPrefs == null){
                    mPrefs = Utility.getSharedPreferences(getActivity());
                }
                Utility.updateNotificationSetting(mPrefs, true);
                Utility.updateDisplayInstantSave(getActivity(), mPrefs, false);
                break;
        }


    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(), WordsContract.TypeEntry.CONTENT_URI,
                null,
                null,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(Loader loader, Cursor data) {
        mListViewAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader loader) {
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
        public void bindView(View view, Context context, Cursor cursor) {
            ViewHolder v = (ViewHolder) view.getTag();
            v.word_type.setText(cursor.getString(cursor.getColumnIndex(WordsContract.TypeEntry.COL_NAME)));

        }

        static class ViewHolder {
            TextView word_type;

            public ViewHolder(View v) {
                word_type = (TextView) v.findViewById(android.R.id.text1);

            }
        }
    }
}
