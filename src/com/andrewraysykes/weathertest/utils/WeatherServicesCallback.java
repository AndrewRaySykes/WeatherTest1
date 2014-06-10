package com.andrewraysykes.weathertest.utils;

import org.json.JSONObject;

public interface WeatherServicesCallback {
	void execute(JSONObject weatherData);
}