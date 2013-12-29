package com.example.mapsv2;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements LocationListener {

	LocationManager locationManager;
	Double lat = null;
	Double lon = null;
	Resources res;
	GoogleMap map;
	private final String API_key = "AIzaSyBuAavokNBAKpvkJwZT193S0b2j66pJ3lc";
	Context context;
	AutoCompleteTextView searchField;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		context = this;
		res = this.getResources();
		map = ((SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map)).getMap();

		/*
		 * map.setOnMapClickListener(new OnMapClickListener() {
		 * 
		 * @Override public void onMapClick(LatLng point) {
		 * Toast.makeText(getBaseContext(), point.latitude + " " +
		 * point.longitude, Toast.LENGTH_LONG).show(); transit(point); } });
		 */

		searchField = (AutoCompleteTextView) findViewById(R.id.editTextSearchTxt);

		searchField.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				System.out.println("************text is " + s.toString());
				GetPlacesData placesData = new GetPlacesData();
				placesData.execute(s.toString());
			}
		});

		/*searchField.setAdapter(new PlacesAutoCompleteAdapter(context,
				android.R.layout.simple_dropdown_item_1line));*/

		searchField.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterView, View view,
					int position, long id) {
				String str = (String) adapterView.getItemAtPosition(position);
				Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
			}

		});

		Button go = (Button) findViewById(R.id.searchButton);
		go.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (searchField.getText().toString() != null
						|| !searchField.getText().toString().equals("")) {
					CallGooglePlaces callPlaces = new CallGooglePlaces();
					StringBuilder url = new StringBuilder();
					/*
					 * /https://maps.googleapis.com/maps/api/place/
					 * nearbysearch/json?location=-33.8670522,151.1957362&
					 * radius=500& types=food& name=harbour& sensor=false&
					 * key=AddYourOwnKeyHere
					 */
					url.append("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
					url.append("location=" + lat + "," + lon + "&");
					url.append("radius=" + "3000" + "&");
					url.append("keyword="
							+ searchField.getText().toString().trim() + "&");
					url.append("sensor=" + "false" + "&");
					url.append("key=" + API_key);
					callPlaces.execute(url.toString());
				}
			}
		});

		// setting the parameters for the loction manager.
		long minTimeBetweenUpdates = 1000 * 60;
		float minDistBetweenUpdates = 100;

		try {
			// Acquire a reference to the system Location Manager
			locationManager = (LocationManager) this
					.getSystemService(Context.LOCATION_SERVICE);

			// Register the listener with the Location Manager to receive
			// location updates
			locationManager.requestLocationUpdates(
					LocationManager.NETWORK_PROVIDER, minTimeBetweenUpdates,
					minDistBetweenUpdates, this);

		} catch (Exception e) {

		}
	}

	void transit(LatLng point) {
		Intent intent = new Intent(this, CreateEventActivity.class);
		intent.putExtra("previousActivity", "mapActivity");
		intent.putExtra("latitude", point.latitude);
		intent.putExtra("longitude", point.longitude);
		MapsActivity.this.startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onLocationChanged(Location location) {
		lat = location.getLatitude();
		lon = location.getLongitude();
		LatLng currentLoc = new LatLng(lat, lon);
		map.addMarker(new MarkerOptions().position(currentLoc).title(
				res.getString(R.string.current_location)));
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLoc, 15));
		map.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
	}

	@Override
	public void onProviderDisabled(String provider) {
	}

	@Override
	public void onProviderEnabled(String provider) {
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		locationManager.removeUpdates(this);
	}

	/**
	 * Based on: http://stackoverflow.com/questions/3505930
	 */
	private class CallGooglePlaces extends
			AsyncTask<String, Void, ArrayList<Place>> {

		@Override
		protected ArrayList<Place> doInBackground(String... args) {
			HttpClient httpclient = new DefaultHttpClient();
			HttpResponse response;
			for (String url : args) {

				System.out.println("****  URL is " + url);
				try {
					String uri = Uri.encode(url, ":/&=?");
					System.out.println("****  URI is " + uri);
					response = httpclient.execute(new HttpGet(uri));
					StatusLine statusLine = response.getStatusLine();
					if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
						String responseStr = EntityUtils.toString(response
								.getEntity());
						System.out.println("Response is " + responseStr);
						try {
							JSONObject jsonObj = new JSONObject(responseStr);
							JSONArray array = jsonObj.getJSONArray("results");
							ArrayList<Place> arrayList = new ArrayList<Place>();
							for (int i = 0; i < array.length(); i++) {
								try {
									Place place = Place
											.jsonToPointOfInterest((JSONObject) array
													.get(i));
									Log.v("Places Services ", "" + place);
									arrayList.add(place);
								} catch (Exception e) {
								}
							}
							return arrayList;
						} catch (JSONException e) {
							e.printStackTrace();
						}
					} else {
						response.getEntity().getContent().close();
						throw new IOException(statusLine.getReasonPhrase());
					}
				} catch (ClientProtocolException e) {
					Log.e("ERROR", e.getMessage());
				} catch (IOException e) {
					Log.e("ERROR", e.getMessage());
				}
			}
			return null;
		}

		@Override
		protected void onPostExecute(ArrayList<Place> result) {
			super.onPostExecute(result);
			if (result == null || result.isEmpty()) {
				Toast.makeText(context, "Not able to retrieve data",
						Toast.LENGTH_LONG).show();
			} else {
				for (int i = 0; i < result.size(); i++) {
					map.addMarker(new MarkerOptions()
							.position(
									new LatLng(result.get(i).getLatitude(),
											result.get(i).getLongitude()))
							.flat(true).title(result.get(i).getName()));
				}
			}
		}

	}

	private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
	private static final String TYPE_AUTOCOMPLETE = "/autocomplete";
	private static final String OUT_JSON = "/json";

	class GetPlacesData extends AsyncTask<String, Void, ArrayList<String>> {

		protected ArrayList<String> doInBackground(String... urls) {
			ArrayList<String> resultList = null;
			System.out.println("in do in background");
			HttpURLConnection conn = null;
			StringBuilder jsonResults = new StringBuilder();
			try {
				StringBuilder sb = new StringBuilder(PLACES_API_BASE
						+ TYPE_AUTOCOMPLETE + OUT_JSON);
				sb.append("?location=" + lat + "," + lon);
				sb.append("&radius=" + "3000");
				sb.append("&input=" + URLEncoder.encode(urls[0], "utf8"));
				sb.append("&sensor=false&key=" + API_key);
				
				System.out.println("***************input received is " + urls[0]);
				System.out.println("*************** url generated is " + sb.toString());
				URL url = new URL(sb.toString());
				conn = (HttpURLConnection) url.openConnection();
				InputStreamReader in = new InputStreamReader(
						conn.getInputStream());

				// Load the results into a StringBuilder
				int read;
				char[] buff = new char[1024];
				while ((read = in.read(buff)) != -1) {
					jsonResults.append(buff, 0, read);
				}
			} catch (MalformedURLException e) {
				Log.e("", "Error processing Places API URL", e);
				return resultList;
			} catch (IOException e) {
				Log.e("", "Error connecting to Places API", e);
				return resultList;
			} finally {
				if (conn != null) {
					conn.disconnect();
				}
			}

			try {
				// Create a JSON object hierarchy from the results
				JSONObject jsonObj = new JSONObject(jsonResults.toString());
				JSONArray predsJsonArray = jsonObj.getJSONArray("predictions");
 
				// Extract the Place descriptions from the results
				resultList = new ArrayList<String>(predsJsonArray.length());
				for (int i = 0; i < predsJsonArray.length(); i++) {
					resultList.add(predsJsonArray.getJSONObject(i).getString(
							"description"));
					System.out.println("***** result received is " + predsJsonArray.getJSONObject(i).getString(
							"description"));
				}
			} catch (JSONException e) {
				Log.e("", "Cannot process JSON results", e);
			}

			return resultList;
		}

		protected void onPostExecute(ArrayList<String> feed) {
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, feed);
			searchField.setAdapter(adapter);
		}
	}
}
