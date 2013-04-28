package com.example.maps;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends FragmentActivity {

	/**
	 * @see
	 * 詳細過程請參考這個 Google doc。裡面也有講到如何使用範例。
	 * https://docs.google.com/document/d/1br1E7v2OUqpFACC-Q_zUX0EnXFcM9IVd_d1SSo2Ykws/edit
	 */
	private SupportMapFragment mapFragment;
	private GoogleMap map;
	private LocationManager locMgr;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		locMgr = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		getLastKnownLocation();

		mapFragment = (SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map);
		map = mapFragment.getMap();

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

		return new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}