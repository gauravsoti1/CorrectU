package androidavengers.correctu.splash_screen;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;

import androidavengers.correctu.R;
import androidavengers.correctu.data.WordsContract;
import androidavengers.correctu.home_screen.HomePageActivity;
import androidavengers.correctu.utility.Utility;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SplashScreenActivity extends AppCompatActivity {

    private static final int AUTO_HIDE_DELAY_MILLIS = 2000;
    private static final String TAG = SplashScreenActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        final SharedPreferences mPrefs = Utility.getSharedPreferences(this);
        Thread x = new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "Currently running thread = " + Thread.currentThread().getName());
                if (!Utility.checkIfTypeDataAdded(mPrefs)) {
                    try {
                        ContentValues[] cv = new ContentValues[2];
                        cv[0] = new ContentValues();
                        cv[1] = new ContentValues();
                        cv[0].put(WordsContract.TypeEntry.COL_NAME, "Saved Words");
                        cv[1].put(WordsContract.TypeEntry.COL_NAME, "Commonly Misspelt");

                        int xx = getContentResolver().bulkInsert(WordsContract.TypeEntry.CONTENT_URI, cv);
                        if (xx != -1) {
                            Utility.updateTypeData(mPrefs, true);
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "Couldn't add type data baecause " + e.toString());
                    } finally {
                        Log.d(TAG, "Currently finished thread = data save");
                    }
                }

            }


        }, "Save Type Data");
        x.start();


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    /*WordsDbHelper dbhelper = new WordsDbHelper(SplashScreenActivity.this);
                    SQLiteDatabase db = dbhelper.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put();
                    db.insert(WordsContract.TypeEntry.TABLE_NAME, null, values);
                    values.clear();
                    values.put(WordsContract.TypeEntry.COL_NAME, );
                    db.insert(WordsContract.TypeEntry.TABLE_NAME, null, values);*/
                    Log.d(TAG, "Currently running thread = " + Thread.currentThread().getName());
                    Thread.sleep(AUTO_HIDE_DELAY_MILLIS);


                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    Log.d(TAG, "Currently finished thread = sleeping thread");
                    startActivity(new Intent(SplashScreenActivity.this, HomePageActivity.class));
                    finish();
                }
            }
        }, "Sleeping Thread").start();

    }


}
