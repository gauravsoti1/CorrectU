package androidavengers.correctu.storage_room_screen;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.textservice.SentenceSuggestionsInfo;
import android.view.textservice.SpellCheckerSession;
import android.view.textservice.SuggestionsInfo;
import android.view.textservice.TextInfo;
import android.view.textservice.TextServicesManager;
import android.widget.EditText;
import android.widget.Toast;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.jakewharton.rxbinding.widget.TextViewTextChangeEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import androidavengers.correctu.R;
import androidavengers.correctu.utility.WordsData;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;

/**
 * A placeholder fragment containing a simple view.
 */
public class StorageRoomActivityFragment extends Fragment implements /*WordSuggestionFragment.OnListFragmentInteractionListener,*/ StorageRoomFragmentView {


    private static final String TAG = StorageRoomActivityFragment.class.getSimpleName();
    private WordSuggestionFragment mWordSuggestionFrag;
    private StorageRoomFragmentPresenter mPresenter;
    private EditText mEnterWordEdt;
    private SpellCheckerSession mScs;

    public StorageRoomActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mPresenter = new StorageRoomFragmentPresenter(this);
        View v = inflater.inflate(R.layout.fragment_storage_room, container, false);
        mEnterWordEdt = (EditText) v.findViewById(R.id.enter_word_edt);
        mPresenter.setEditTextChangeTask();
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        final TextServicesManager tsm = (TextServicesManager) getActivity().getSystemService(
                Context.TEXT_SERVICES_MANAGER_SERVICE);
        mScs = tsm.newSpellCheckerSession(null, null, this, true);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Toast.makeText(getActivity(), newConfig.toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mScs != null) {
            mScs.close();
        }
        if (mWordSuggestionFrag != null) {
            mWordSuggestionFrag = null;
        }
    }


    /*@Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {

    }*/


    @Override
    public WordSuggestionFragment getWordSuggestionFragment() {
        if (null == mWordSuggestionFrag) {
            mWordSuggestionFrag = (WordSuggestionFragment) (getChildFragmentManager().findFragmentById(R.id.word_suggestion_fragment));
        }
        return mWordSuggestionFrag;
        /*mWordSuggestionFrag.OnWordSuggestionChanged(mPresenter.loadWordSuggestion(word));*/
    }

    @Override
    public void onWordEnteredChange() {
        RxTextView.textChangeEvents(mEnterWordEdt)
                .debounce(400, TimeUnit.MILLISECONDS)
                .filter(new Func1<TextViewTextChangeEvent, Boolean>() {
                    @Override
                    public Boolean call(TextViewTextChangeEvent textViewTextChangeEvent) {
                        if(textViewTextChangeEvent.count()==0) {
                            return false;
                        }
                        return true;
                    }
                })// default Scheduler is Computation
                .observeOn(AndroidSchedulers.mainThread())//
                .subscribe(mPresenter._getSearchObserver());
    }

    @Override
    public List<WordsData> loadWordSuggestion(final String word) {
        if (mScs != null) {

            // Note that getSuggestions() is a deprecated API.
            // It is recommended for an application running on Jelly Bean or later
            // to call getSentenceSuggestions() only.
            mScs.getSuggestions(new TextInfo(word), 15);
                /*mScs.getSuggestions();
                mScs.getSuggestions(new TextInfo("hllo"), 3);
                mScs.getSuggestions(new TextInfo("helloworld"), 3);*/

        } else {
            Log.e(TAG, "Couldn't obtain the spell checker service.");
        }
        return null;
    }


    @Override
    public void onGetSuggestions(SuggestionsInfo[] results) {
        if(results==null){
            return;
        }

        ArrayList<WordsData> wordDataList = new ArrayList<WordsData>();

        for (int ii =0;ii<results.length;ii++) {

            for (int j=0;j<results[ii].getSuggestionsCount();j++) {
                WordsData word = new WordsData();
                word.setWord(results[ii].getSuggestionAt(j));
               // Toast.makeText(getActivity(),results[ii].getSuggestionAt(j),Toast.LENGTH_LONG).show();
                wordDataList.add(word);

            }
        }


        mPresenter.getWordSuggestionFragment().OnWordSuggestionChanged(wordDataList);
    }

    @Override
    public void onGetSentenceSuggestions(SentenceSuggestionsInfo[] results) {

        /*final StringBuilder sb = new StringBuilder();
        final SentenceSuggestionsInfo ssi = results[0];
        *//*for (int j = 0; j < ssi.getSuggestionsCount(); ++j) {*//*


       *//* }*//*
        mPresenter.getWordSuggestionFragment().OnWordSuggestionChanged(dumpSuggestionsInfoInternal(
                sb, ssi.getSuggestionsInfoAt(0), ssi.getOffsetAt(0), ssi.getLengthAt(0)));*/
    }

    private ArrayList<WordsData> dumpSuggestionsInfoInternal(
            final StringBuilder sb, final SuggestionsInfo si, final int length, final int offset) {
        ArrayList<WordsData> wordDataList = new ArrayList<WordsData>();
        // Returned suggestions are contained in SuggestionsInfo
        final int len = si.getSuggestionsCount();
        sb.append('\n');
        for (int j = 0; j < len; ++j) {
            if (j != 0) {
                sb.append(", ");
            }
            WordsData word = new WordsData();
            word.setWord(si.getSuggestionAt(j));
            wordDataList.add(word);
        }
        return wordDataList ;
    }
}
