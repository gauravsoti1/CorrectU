package androidavengers.correctu.utility;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

import androidavengers.correctu.R;
import androidavengers.correctu.storage_room_screen.StorageRoomActivity;

/**
 * Created by hp on 1/16/2016.
 */
public class Utility {

    public static SharedPreferences getSharedPreferences(Context context) {
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        return mPrefs;
    }

    public static boolean checkWhetherDisplayedNotificationSettingCardView(Context context) {
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        ;
        return mPrefs.getBoolean(CommonConstants.INSTRUCTION_VIEWED_KEY, false);

    }

    public static void updateNotificationSetting(SharedPreferences mPrefs, boolean value) {

        /*SharedPreferences mPrefs = context.getSharedPreferences(CommonConstants.APP_SHAREDPREF_NAME, Context.MODE_PRIVATE);*/
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putBoolean(CommonConstants.INSTRUCTION_VIEWED_KEY, value);
        editor.commit();

    }

    public static void updateDisplayInstantSave(Context context, SharedPreferences mPrefs, boolean value) {

        /*SharedPreferences mPrefs = context.getSharedPreferences(CommonConstants.APP_SHAREDPREF_NAME, Context.MODE_PRIVATE);*/
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putBoolean(context.getString(R.string.instant_save_notification_key), value);
        editor.commit();

    }




    public static boolean checkIfTypeDataAdded(SharedPreferences mPrefs) {
        return mPrefs.getBoolean(CommonConstants.TYPE_DATA_ADDED_KEY, false);
    }

    public static void updateTypeData(SharedPreferences mPrefs, boolean value) {
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putBoolean(CommonConstants.TYPE_DATA_ADDED_KEY, value);
        editor.commit();

    }

    public static void displayPersistentNotification(Context context) {

        Intent intent = new Intent(context, StorageRoomActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,
                CommonConstants.OPEN_STORAGE_SCREEN_NOTIFICATION_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context.getApplicationContext());
        builder.setContentTitle("CorrectU");
        builder.setContentText("Instant Save");
        builder.setColor(ContextCompat.getColor(context, R.color.colorPrimary));
        builder.setNumber(101);
        builder.setContentIntent(pendingIntent);
        builder.setTicker("Instant Save");
        builder.setSmallIcon(R.mipmap.ic_launcher);
        //builder.setLargeIcon(R.drawable.ic_timer);
        builder.setAutoCancel(false);
        builder.setPriority(0);
        builder.setOngoing(true);
        NotificationManager mNotifyMgr =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotifyMgr.notify(CommonConstants.OPEN_STORAGE_SCREEN_NOTIFICATION_ID, builder.build());


    }

}
