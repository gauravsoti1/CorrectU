package androidavengers.correctu.storage_room_screen;

import android.content.ContentValues;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import androidavengers.correctu.R;
import androidavengers.correctu.data.WordsContract;
import androidavengers.correctu.storage_room_screen.dummy.DummyContent.DummyItem;
import androidavengers.correctu.utility.RetrofitApiManager;
import androidavengers.correctu.utility.WordPojo;
import androidavengers.correctu.utility.WordsData;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/*import androidavengers.correctu.storage_room_screen.WordSuggestionFragment.OnListFragmentInteractionListener;*/

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified { OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyWordListViewAdapter extends ArrayAdapter<WordsData> {
    private static final String TAG = MyWordListViewAdapter.class.getSimpleName() ;
    /*private final OnListFragmentInteractionListener mListener;*/
    private final Context context;

    public MyWordListViewAdapter(List<WordsData> items/*, OnListFragmentInteractionListener listener*/, Context context) {
        super(context, R.layout.word_suggestion_list_item, items);
        /*mListener = listener;*/
        this.context = context;

    }

    @Override
    public WordsData getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.word_suggestion_list_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final String word = getItem(position).getWord();
        holder.mWordSuggestionTextView.setText(word);
        holder.mMeaningClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RetrofitApiManager.getInstance().getWordApiService().gettingWordMeaning(word, new Callback<List<WordPojo>>() {
                    @Override
                    public void success(List<WordPojo> wordPojo, Response response) {
                        if(wordPojo.size()==0){
                            Toast.makeText(context,"Unfortunately, the meaning is not available!",Toast.LENGTH_LONG).show();
                            return;
                        }
                        getItem(position).setMeaning(wordPojo.get(0).getDefinitions()[0]);
                        getItem(position).setPart_of_speech(wordPojo.get(0).getPartOfSpeech());
                        notifyDataSetChanged();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        //TODO: remove this Toast for production build
                        Toast.makeText(context,error.toString(),Toast.LENGTH_LONG).show();
                    }
                });
                //Toast.makeText(context, "Meaning button clicked", Toast.LENGTH_LONG).show();
            }
        });
        final String wordMeaning = getItem(position).getMeaning();
        if (wordMeaning == null) {
            holder.mWordMeaning.setVisibility(View.GONE);
            holder.mMeaningClick.setVisibility(View.VISIBLE);
        } else {
            holder.mWordMeaning.setText("(" + getItem(position).getPart_of_speech() + ")" + " " + wordMeaning);
            holder.mWordMeaning.setVisibility(View.VISIBLE);
            holder.mMeaningClick.setVisibility(View.GONE);
        }

        holder.mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG, "Currently running thread = " + Thread.currentThread().getName());
                            try {
                                ContentValues cv = new ContentValues();
                                cv.put(WordsContract.WordEntry.COL_NAME, getItem(position).getWord());
                                cv.put(WordsContract.WordEntry.COL_TYPE_ID, 0);
                                if(wordMeaning!=null){
                                    cv.put(WordsContract.WordEntry.COL_MEANING, wordMeaning);
                                    cv.put(WordsContract.WordEntry.COL_PART_OF_SPEECH, getItem(position).getPart_of_speech());
                                }


                                context.getContentResolver().insert(WordsContract.WordEntry.CONTENT_URI, cv);
                                //Toast.makeText(context, "Word Saved", Toast.LENGTH_SHORT).show();

                            } catch (Exception e) {
                                /*Log.e(TAG, "Couldn't add type data baecause " + e.toString());*/
                                //Toast.makeText(context,"Couldn't save word. Please try again!",Toast.LENGTH_SHORT).show();
                            } finally {
                                Log.d(TAG, "Currently finished thread = data save");
                            }
                        }
                }, "Save Type Data").start();
            }
        });

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    *//*mListener.onListFragmentInteraction(holder.mItem);*//*
                }*/
            }
        });

        return convertView;
    }


    public class ViewHolder {
        public final View mView;
        public final TextView mWordSuggestionTextView;
        public final TextView mMeaningClick;
        public final TextView mWordMeaning;
        public final Button mSaveButton;
        public WordsData word;

        public ViewHolder(View view) {
            mView = view;
            mWordSuggestionTextView = (TextView) view.findViewById(R.id.word_suggestion);
            mMeaningClick = (TextView) view.findViewById(R.id.meaning_click);
            mSaveButton = (Button) view.findViewById(R.id.save_word_button);
            mWordMeaning = (TextView) view.findViewById(R.id.word_meaning);
        }

    }
}
