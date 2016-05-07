package androidavengers.correctu.storage_room_screen;

import com.jakewharton.rxbinding.widget.TextViewTextChangeEvent;

import rx.Observer;

/**
 * Created by hp on 1/26/2016.
 */
public class StorageRoomFragmentPresenter {

    private final StorageRoomFragmentView view;

    public StorageRoomFragmentPresenter(StorageRoomFragmentView view) {
        this.view = view;
    }

    WordSuggestionFragment getWordSuggestionFragment() {
        WordSuggestionFragment frag = view.getWordSuggestionFragment();
        return frag;
        //frag.OnWordSuggestionChanged();
    }


    public void setEditTextChangeTask() {
        view.onWordEnteredChange();
    }

    public Observer<TextViewTextChangeEvent> _getSearchObserver() {
        return new Observer<TextViewTextChangeEvent>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(TextViewTextChangeEvent onTextChangeEvent) {
                view.loadWordSuggestion(onTextChangeEvent.text()+"");
            }
        };
    }
}
