package com.example.mapsv2;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

public class CreateEventActivity extends Activity implements OnClickListener {

	DatePickerDialog DPD;
	TimePickerDialog TPD;
	Button eventDate;
	Button eventTime;
	Button pickLoaction;
	Context context = this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_event);
		pickLoaction = (Button) findViewById(R.id.event_location);
		Intent intent = this.getIntent();
		System.out.println("**********" + intent.getExtras());
		if (intent.getExtras() != null
				&& intent.getExtras().get("previousActivity") != null
				&& intent.getExtras().get("previousActivity")
						.equals("mapActivity")) {
			pickLoaction.setText("" + intent.getExtras().getDouble("latitude"));
		}
		eventDate = (Button) findViewById(R.id.event_date);
		eventTime = (Button) findViewById(R.id.event_time);
		pickLoaction.setOnClickListener(this);
		eventDate.setOnClickListener(this);
		eventTime.setOnClickListener(this);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_event, menu);
		return true;
	}

	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			String date = dayOfMonth + "-" + monthOfYear + "-" + year;
			eventDate.setText(date);
		}
	};

	private TimePickerDialog.OnTimeSetListener mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {

		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			String time = hourOfDay + ":" + minute;
			eventTime.setText(time);
		}
	};

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.event_location) {
			Intent i = new Intent(context, MapsActivity.class);
			CreateEventActivity.this.startActivity(i);
		} else if (id == R.id.event_date) {
			DPD = new DatePickerDialog(context, mDateSetListener, 2013, 11, 30);
			DPD.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
			DPD.show();
		} else if (id == R.id.event_time) {
			TPD = new TimePickerDialog(context, mTimeSetListener,
					Calendar.HOUR_OF_DAY, Calendar.MINUTE, true);
			TPD.show();
		}

	}
}
