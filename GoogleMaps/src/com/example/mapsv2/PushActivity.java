package com.example.mapsv2;

import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseInstallation;
import com.parse.PushService;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

public class PushActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Parse.initialize(this, "vMFTELLhOo9RDRql9HpV9lKRot5xQTCCD63wkYdQ", "mdz7n8XUjy3u0MSQRnuwmogqXZrw3qJnRwmRxx0g"); 
		PushService.setDefaultPushCallback(this, PushActivity.class);
		ParseInstallation.getCurrentInstallation().saveInBackground();
		
		Context context = getApplicationContext();
		CharSequence text = ParseInstallation.getCurrentInstallation().getObjectId();
		int duration = Toast.LENGTH_LONG;

		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
		
		
		toast = Toast.makeText(context, "Aplication opened", duration);
		toast.show();
		
		ParseAnalytics.trackAppOpened(getIntent());
		setContentView(R.layout.activity_main);
		
		/*
		 * Notification from inside the app :: STARTS.
		 */
		
		NotificationCompat.Builder notification= new NotificationCompat.Builder(this)
	    .setSmallIcon(R.drawable.ic_launcher)
	    .setContentTitle("My notification")
	    .setContentText("Hello World!");
		
		// Sets an ID for the notification
		int mNotificationId = 001;
		// Gets an instance of the NotificationManager service
		NotificationManager mNotifyMgr = 
		        (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		// Builds the notification and issues it.
		mNotifyMgr.notify(mNotificationId, notification.build());
		
		/*
		 * Notification from inside the app :: ENDS.
		 */
		

	}
}
