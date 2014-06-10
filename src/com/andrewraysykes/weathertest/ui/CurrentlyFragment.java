package com.andrewraysykes.weathertest.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.andrewraysykes.weathertest.utils.WeatherIcon;
import com.example.weathertest.R;

public class CurrentlyFragment extends Fragment{

	public static final String TAG = CurrentlyFragment.class.getSimpleName();
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_currently, container, false);
		
		ImageView mWeatherIcon = (ImageView)rootView.findViewById(R.id.weather_icon);
		
		// Example of changing the weather icon in this class.
		// In the future there will be a case statement to properly
		//  choose this image based on the weather conditions from
		//  the JSON data.
		
		mWeatherIcon.setImageResource(WeatherIcon.getIcon("fuckingshit"));
		
		return rootView;
	}
	
}
