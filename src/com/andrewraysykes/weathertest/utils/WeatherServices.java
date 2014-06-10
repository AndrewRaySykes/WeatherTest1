package com.andrewraysykes.weathertest.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.weathertest.R;

public class WeatherServices {
	protected static final String TAG = WeatherServices.class.getSimpleName();
	protected Context mContext;

	public WeatherServices(Context context) {
		mContext = context;
	}

	public void getWeatherData(double[] coordinates, String units,
			WeatherServicesCallback callback) {

		if (isNetworkAvailable(mContext)) {
			GetWeatherData weatherData = new GetWeatherData(callback);

			double unitLong = 0;
			switch (units) {
			case "Metric":
				unitLong = 0;
				break;

			case "Imperial":
				unitLong = 1;
				break;
			}

			weatherData.execute(coordinates[0], coordinates[1], unitLong);
		} else {
			Toast.makeText(mContext,
					mContext.getString(R.string.error_no_connection),
					Toast.LENGTH_LONG).show();
		}
	}

	public boolean isNetworkAvailable(Context context) {
		ConnectivityManager manager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = manager.getActiveNetworkInfo();

		boolean isAvailable = false;
		if (networkInfo != null && networkInfo.isConnected()) {
			isAvailable = true;
		}

		return isAvailable;
	}

	private class GetWeatherData extends AsyncTask<Double, Void, JSONObject> {
		final WeatherServicesCallback mCallback;

		public GetWeatherData(WeatherServicesCallback callback) {
			mCallback = callback;
		}

		protected JSONObject doInBackground(Double... params) {
			// performing request
			JSONObject jsonResponse = new JSONObject();
			int responseCode = -1;

			String units = "ca";
			switch (params[2].intValue()) {
			case 0:
				units = "ca";
				break;

			case 1:
				units = "us";
				break;
			}

			StringBuilder builder = new StringBuilder();
			HttpClient client = new DefaultHttpClient();

			// getting forecast.io data
			HttpGet httpget = new HttpGet("https://api.forecast.io/forecast/"
					+ WeatherTestConstants.FORECAST_API_KEY + "/" + params[0] + ","
					+ params[1] + "?units=" + units);

			try {
				HttpResponse response = client.execute(httpget);
				StatusLine statusLine = response.getStatusLine();
				responseCode = statusLine.getStatusCode();

				if (responseCode == HttpURLConnection.HTTP_OK) {
					HttpEntity entity = response.getEntity();
					InputStream content = entity.getContent();
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(content));
					String line;
					while ((line = reader.readLine()) != null) {
						builder.append(line);
					}

					jsonResponse = new JSONObject(builder.toString());

					// performing geolocation with Google maps
					StringBuilder builderLocation = new StringBuilder();
					HttpClient clientLocation = new DefaultHttpClient();
					HttpGet httpgetLocation = new HttpGet(
							"https://maps.googleapis.com/maps/api/geocode/json?latlng="
									+ params[0] + "," + params[1]);
					HttpResponse responseLocation = clientLocation
							.execute(httpgetLocation);
					StatusLine statusLineLocation = responseLocation
							.getStatusLine();
					int responseCodeLocation = statusLineLocation
							.getStatusCode();

					if (responseCodeLocation == HttpURLConnection.HTTP_OK) {
						HttpEntity entityLocation = responseLocation
								.getEntity();
						InputStream contentLocation = entityLocation
								.getContent();
						BufferedReader readerLocation = new BufferedReader(
								new InputStreamReader(contentLocation));
						String lineLocation;
						while ((lineLocation = readerLocation.readLine()) != null) {
							builderLocation.append(lineLocation);
						}

						JSONObject locationData = new JSONObject(
								builderLocation.toString());
						jsonResponse.put("LocationData", locationData);
					} else {
						Log.i("REQUEST", "Unsuccessfull HTTP Response Code: "
								+ responseCodeLocation);
					}
				} else {
					Log.i("REQUEST", "Unsuccessfull HTTP Response Code: "
							+ responseCode);
				}
			} catch (JSONException e) {
				Log.e(TAG, "JSON: " + e.toString());
			} catch (IOException e) {
				Log.e(TAG, "IO: " + e.toString());
			}

			return jsonResponse;
		}

		protected void onPostExecute(JSONObject weatherData) {
			mCallback.execute(weatherData);
		}
	}
}
