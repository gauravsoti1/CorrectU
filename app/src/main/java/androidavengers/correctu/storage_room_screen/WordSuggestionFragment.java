package androidavengers.correctu.storage_room_screen;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.textservice.SentenceSuggestionsInfo;
import android.view.textservice.SpellCheckerSession;
import android.view.textservice.SuggestionsInfo;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import androidavengers.correctu.R;
import androidavengers.correctu.utility.WordsData;

/**
 * A fragment representing a list of Items.
 * <p>
 * Activities containing this fragment MUST implement the {OnListFragmentInteractionListener}
 * interface.
 */
public class WordSuggestionFragment extends Fragment implements SpellCheckerSession.SpellCheckerSessionListener {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 3;
    /*private OnListFragmentInteractionListener mListener;*/
    private List<WordsData> mWordsData = new ArrayList<WordsData>();
    private MyWordListViewAdapter mListViewAdapter;
    private SpellCheckerSession mScs;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public WordSuggestionFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static WordSuggestionFragment newInstance(int columnCount) {
        WordSuggestionFragment fragment = new WordSuggestionFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.word_suggestion_frag, container, false);

        // Set the adapter

        ListView wordsSuggestionListView = (ListView) view.findViewById(R.id.word_suggestion_list);
            /*if (mColumnCount <= 1) {

            } else {*/
        //recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            /*}*/

        //wordsSuggestionListView.setAdapter(new MyWordListViewAdapter(, mListener));
        mListViewAdapter = new MyWordListViewAdapter(mWordsData/*, mListener*/, getActivity());
        wordsSuggestionListView.setAdapter(mListViewAdapter);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        /*if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }*/
    }


    @Override
    public void onDetach() {
        super.onDetach();
        /*mListener = null;*/
    }

    public void OnWordSuggestionChanged(List<WordsData> wordsData) {
        mWordsData.clear();
        mWordsData.addAll(wordsData);
        mListViewAdapter.notifyDataSetChanged();
        /*mListViewAdapter.clear();
        mListViewAdapter.addAll(wordsData);*/
    }

    /**
     * Callback for { SpellCheckerSession#getSuggestions(TextInfo, int)}
     * and { SpellCheckerSession#getSuggestions(TextInfo[], int, boolean)}
     *
     * @param results an array of {@link SuggestionsInfo}s.
     *                These results are suggestions for { TextInfo}s queried by
     *                { SpellCheckerSession#getSuggestions(TextInfo, int)} or
     *                { SpellCheckerSession#getSuggestions(TextInfo[], int, boolean)}
     */
    @Override
    public void onGetSuggestions(SuggestionsInfo[] results) {

    }


    @Override
    public void onGetSentenceSuggestions(SentenceSuggestionsInfo[] results) {

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    /*public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(DummyItem item);
    }*/
}
