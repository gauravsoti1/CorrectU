package androidavengers.correctu.utility;

import android.service.textservice.SpellCheckerService;
import android.view.textservice.SuggestionsInfo;
import android.view.textservice.TextInfo;

/**
 * Created by hp on 1/26/2016.
 */
public class MySpellCheckerService extends SpellCheckerService {

    @Override
    public Session createSession() {
        return new AndroidSpellCheckerSession();
    }

    private static class AndroidSpellCheckerSession extends Session{

        @Override
        public void onCreate() {

        }

        @Override
        public SuggestionsInfo onGetSuggestions(TextInfo textInfo, int suggestionsLimit) {
            return null;
        }
    }
}
