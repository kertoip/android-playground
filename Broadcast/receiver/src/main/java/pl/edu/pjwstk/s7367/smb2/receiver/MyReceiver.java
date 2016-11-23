package pl.edu.pjwstk.s7367.smb2.receiver;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import static android.content.Context.NOTIFICATION_SERVICE;

public class MyReceiver extends BroadcastReceiver {

    public static final String MESSAGE_KEY = "message";
    public static final String SEND_ACTIVITY_PACKAGE = "pl.edu.pjwstk.s7367.smb2.sender";

    public static int NOTIFICATION_ID = 1;

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent notificationIntent = context.getPackageManager().getLaunchIntentForPackage(SEND_ACTIVITY_PACKAGE);
        PendingIntent notificationPendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(android.R.drawable.sym_def_app_icon)
                        .setContentTitle(context.getString(R.string.notification_title))
                        .setContentText(intent.getStringExtra(MESSAGE_KEY))
                        .setContentIntent(notificationPendingIntent);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify((NOTIFICATION_ID+=1), notificationBuilder.build());
    }
}
