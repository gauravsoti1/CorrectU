package androidavengers.correctu.enhance_time_screen;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.jakewharton.rxbinding.widget.TextViewTextChangeEvent;

import java.util.concurrent.TimeUnit;

import androidavengers.correctu.R;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

public class EnhanceTimeScreenActivity extends AppCompatActivity {

    private CardView spellingDisplayCardviewContainer;
    private TextView timerText;
    private TextView displaySpelling;
    private TextView displayEncouragement;
    private Button cancelOrDoneButton;

    private Subscription _subscription;
    private EditText enterSpellingEdt;
    private HorizontalScrollView displaySpellingContainer;
    private Subscription timerSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enhance_time_screen);

        spellingDisplayCardviewContainer = (CardView) findViewById(R.id.spelling_display_cardview_container);
        timerText = (TextView) findViewById(R.id.timer_text);
        displaySpelling = (TextView) findViewById(R.id.display_spelling);
        displayEncouragement = (TextView) findViewById(R.id.display_encouragement);
        cancelOrDoneButton = (Button) findViewById(R.id.cancel_or_done_btn);
        enterSpellingEdt = (EditText) findViewById(R.id.enter_spelling_edt);
        displaySpellingContainer = (HorizontalScrollView) findViewById(R.id.display_spelling_container);
        /*_resultEmitterSubject = PublishSubject.create();
        _subscription = _resultEmitterSubject.asObservable().subscribe(new Action1<String>() {
            @Override
            public void call(String word) {
                displaySpelling.setText(word);
            }
        });*/

        _subscription = RxTextView.textChangeEvents( enterSpellingEdt )//
                //.debounce(400, TimeUnit.MILLISECONDS)// default Scheduler is Computation
                .observeOn(AndroidSchedulers.mainThread())//
                .subscribe(_getSearchObserver());

        timerSubscription = Observable
                .interval(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Long aLong) {
                        if(aLong>20){
                            //TODO: Set text color to change only once.
                            timerText.setTextColor(ContextCompat.getColor(EnhanceTimeScreenActivity.this, android.R.color.holo_red_dark));
                            if(aLong == 30){
                                if(timerSubscription != null) {
                                    timerSubscription.unsubscribe();
                                }
                            }
                        }

                        timerText.setText(Long.toString(30-aLong));

                    }
                });
        /*enterSpellingEdt.set*/
    }

    private Observer<TextViewTextChangeEvent> _getSearchObserver() {
        return new Observer<TextViewTextChangeEvent>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(TextViewTextChangeEvent onTextChangeEvent) {
                displaySpelling.setText(onTextChangeEvent.text().toString());
                displaySpellingContainer.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
            }
        };
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(_subscription!=null){
            _subscription.unsubscribe();
        }
    }
}
