package com.example.mapsv2;

import java.text.ParseException;
import java.util.ArrayList;

import net.sqlcipher.database.SQLiteDatabase;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.database.CustomDbAdapter;
import com.example.database.helpers.EventListHelper;
import com.example.model.Events;

public class EventList extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.event_list);
		SQLiteDatabase.loadLibs(this);
		CustomDbAdapter dbManager = CustomDbAdapter.getInstance(getBaseContext());
		EventListHelper eventListData = new EventListHelper(dbManager);
		ArrayList<Events> eventDetails=null;
		try {
			 eventDetails = eventListData.getEventDetails();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ListView eventListView = (ListView) findViewById(R.id.eventList);
		
			final ArrayList<String> event_names_list = new ArrayList<String>();
		for(Events ix : eventDetails){
			event_names_list.add(ix.getEventName());
		}
		
		final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,event_names_list);
		eventListView	.setAdapter(adapter);

	    eventListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

	      @SuppressLint("NewApi")
		@Override
	      public void onItemClick(AdapterView<?> parent, final View view,
	          int position, long id) {
	        final String item = (String) parent.getItemAtPosition(position);
	        view.animate().setDuration(2000).alpha(0)
	            .withEndAction(new Runnable() {
	              @Override
	              public void run() {
	                event_names_list.remove(item);
	                adapter.notifyDataSetChanged();
	                view.setAlpha(1);
	              }
	            });
	      }

	    });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.event_list, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId() == R.id.create_event){
			Intent i = new Intent(this, CreateEventActivity.class);
			EventList.this.startActivity(i);
		}
		return super.onOptionsItemSelected(item);
	}
	
	

}
