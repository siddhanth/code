package com.example.mapsv2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

public class RecieveData extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recieve_data);
		Intent intent1 = this.getIntent();

		if (intent1 != null) {
			TextView text = (TextView) findViewById(R.id.setText);
			text.setText(intent1.getExtras().getDouble("latitude") + "");

		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.recieve_data, menu);
		return true;
	}

}
