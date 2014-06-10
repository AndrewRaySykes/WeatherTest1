package com.andrewraysykes.weathertest.utils;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class LocationListenerService implements LocationListener {
	private Context mContext;
	private LocationManager mLocationManager;
	private String mProvider;
	private double[] mCoordinates = new double[] { 0, 0 };

	public LocationListenerService(Context context) {
		mContext = context;

		// Get the location manager
		mLocationManager = (LocationManager) mContext
				.getSystemService(Context.LOCATION_SERVICE);

		Criteria criteria = new Criteria();
		mProvider = mLocationManager.getBestProvider(criteria, false);
		Location location = mLocationManager.getLastKnownLocation(mProvider);

		// Initialize the location fields
		if (location != null) {
			onLocationChanged(location);
		}
	}

	public void onActivityResume() {
		mLocationManager.requestLocationUpdates(mProvider, 400, 1, this);
	}

	public void onActivityPause() {
		mLocationManager.removeUpdates(this);
	}

	public void onLocationChanged(Location location) {
		mCoordinates = new double[] { location.getLatitude(),
				location.getLongitude() };
	}

	public double[] getLocation() {
		return mCoordinates;
	}

	public void onStatusChanged(String provider, int status, Bundle extras) {

	}

	public void onProviderEnabled(String provider) {

	}

	public void onProviderDisabled(String provider) {

	}
}
