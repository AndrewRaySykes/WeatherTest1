package com.andrewraysykes.weathertest.utils;

import com.example.weathertest.R;

public class WeatherIcon {

	public static int getIcon(String forecastSummary) {

		if (forecastSummary.equals("fuckingshit")) {
			return R.drawable.ic_launcher;
		} else {
			return R.drawable.ic_launcher;
		}
	}

	public static int getNightIcon(String forecastSummary) {

		if (forecastSummary.equals("fuckingshit")) {
			return R.drawable.ic_launcher;
		} else {
			return R.drawable.ic_launcher;
		}
	}
}
