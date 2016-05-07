package androidavengers.correctu.storage_room_screen;

import android.view.textservice.SpellCheckerSession;

import java.util.List;

import androidavengers.correctu.utility.WordsData;

/**
 * Created by hp on 1/26/2016.
 */
public interface StorageRoomFragmentView extends SpellCheckerSession.SpellCheckerSessionListener {

    WordSuggestionFragment getWordSuggestionFragment();

    void onWordEnteredChange();

    List<WordsData> loadWordSuggestion(String word);
}
