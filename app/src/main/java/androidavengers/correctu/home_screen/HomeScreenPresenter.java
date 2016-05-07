package androidavengers.correctu.home_screen;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import androidavengers.correctu.R;

/**
 * Created by hp on 1/15/2016.
 */
public class HomeScreenPresenter {
    HomeScreenView view;

    public HomeScreenPresenter(HomeScreenView view) {
        this.view = view;
    }

    void onItemClicked(int type){
        this.view.startDisplayWordsActivity();

    }



}
