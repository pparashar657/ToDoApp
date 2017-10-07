package com.example.pawan.todoapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Icon;
import android.media.RingtoneManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import org.w3c.dom.Text;

public class AlarmReciever extends BroadcastReceiver {

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        String Title=intent.getStringExtra(Constants.TODO);
        String Text = intent.getStringExtra(Constants.LIMIT);
        int id=intent.getIntExtra(Constants.POSITION_KEY,0);
        NotificationCompat.Builder builder=new NotificationCompat.Builder(context);
        builder.setContentTitle(Title);
        builder.setAutoCancel(true);
        builder.setContentText(Text);
        builder.setSmallIcon(R.drawable.icon);
        builder.setVibrate(new long[]{1000, 1000});
        builder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
        Intent i=new Intent(context,MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,id,i,0);
        builder.setContentIntent(pendingIntent);
        Notification notification = builder.build();
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        notificationManager.notify(1,notification);
    }
}
