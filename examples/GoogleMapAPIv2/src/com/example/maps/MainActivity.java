package com.example.maps;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

@SuppressLint("NewApi")
public class MainActivity extends Activity {

	private GoogleMap map;
	private LocationManager locMgr;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		locMgr = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		getLastKnownLocation();

		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
				.getMap();

		LatLng currentLocation = getLastKnownLocation();
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15));
		map.addMarker(new MarkerOptions().position(currentLocation).title(
				"My Location"));

	}

	private LatLng getLastKnownLocation() {
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		String best = locMgr.getBestProvider(criteria, true);
		Location myLocation = locMgr.getLastKnownLocation(best);
		
		return new LatLng(myLocation.getLatitude(),
				myLocation.getLongitude());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}