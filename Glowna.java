package pogoda;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
//import java.net.URLEncoder;


public class Glowna {
    private static final String KEY_VALUE = "3acf8997b3f3492cafd205421202002";
    private static final String KEY = "key";
    private static final String URL_PREFIX = "http://api.worldweatheronline.com/premium/v1/weather.ashx?" + KEY + "=" + KEY_VALUE;
    private static final String LOCATION = "q";
    private static final String NUMBER_OF_DAYS = "num_of_days";
    private static final String FORMAT = "format";

    JSONObject[] weather1Data;
    JSONObject[] weatherData;
    String cityName;
    String url;

    
    	public boolean getWeather(String miasto, int numberOfDays) throws MalformedURLException, IOException, JSONException {
        String response;
        String error;

        URL url = new URL(URL_PREFIX + "&" + LOCATION + "=" + miasto + "&" + FORMAT + "=json" + "&"
                + NUMBER_OF_DAYS + "=" + numberOfDays);


        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        BufferedReader br = new BufferedReader(
                new InputStreamReader(connection.getInputStream())
        );
        
        System.out.println("\n"
        		+ "Dane pobrano z " + url);

        while ((response = br.readLine()) != null) {
            JSONObject obj = new JSONObject(response);
            weatherData = new JSONObject[numberOfDays];
            weather1Data = new JSONObject[numberOfDays];
            try {
                cityName = getCityNameFromJson(obj);
                for (int i = 0; i < weatherData.length; i++) {
                    weatherData[i] = (JSONObject) obj.getJSONObject("data").getJSONArray("weather").get(i);
                }
                for (int i = 0; i < weather1Data.length; i++) {
                    weather1Data[i] = (JSONObject) obj.getJSONObject("data").getJSONArray("current_condition").getJSONObject(0);
                }
            } catch (JSONException s) {
                obj = (JSONObject) obj.getJSONObject("data").getJSONArray("error").get(0);
                error = obj.get("msg").toString();

                if ("Unable to find any matching weather location to the query submitted!".equals(error)) {
                    System.out.println("Nieprawidłowa nazwa miasta.");
                    return false;
                }
                return false;
            }
        }
        br.close();
        return true;
    }


    String getForecastDay(int index) throws JSONException {
        return weatherData[index].get("date").toString();
    }

    String getMinTempC(int index) throws JSONException {
        return weatherData[index].get("mintempC").toString();
    }

    String getMaxTempC(int index) throws JSONException {
        return weatherData[index].get("maxtempC").toString();
    }

    String getTemp1(int index) throws JSONException {
    	return weather1Data[index].get("temp_C").toString();
    }
    
    String getWiatr(int index) throws JSONException {
        return weatherData[index].getJSONArray("hourly").getJSONObject(0).get("WindGustKmph").toString();
    }
    
    
    String getCisnienie(int index) throws JSONException {
        return weatherData[index].getJSONArray("hourly").getJSONObject(0).get("pressure").toString();
    }
    
    String getTemp(int index) throws JSONException {
        return weatherData[index].getJSONArray("hourly").getJSONObject(0).get("tempC").toString();
    }

    String getHumidity(int index) throws JSONException {
        return weatherData[index].getJSONArray("hourly").getJSONObject(0).get("humidity").toString();
    }

    private String getCityNameFromJson(JSONObject obj) throws JSONException {
        return obj.getJSONObject("data").getJSONArray("request").getJSONObject(0).get("query").toString();
    }
}



