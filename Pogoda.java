package pogoda;

import org.json.JSONException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Scanner;
import java.nio.charset.StandardCharsets;


public class Pogoda {
    private static Glowna api;

    public static void main(String[] args) throws IOException, JSONException {
        Scanner scan = new Scanner(System.in);
        api = new Glowna();
        String miasto;
        int countDays;

        System.out.println("Proszê wpisaæ nazwê miejscowoœci, której chcesz sprawdziæ stan pogody:");
        miasto = getCity(scan.nextLine());
        System.out.println("Proszê okresliæ iloœæ dni, licz¹c od dnia dzisiejszego, na ile chcesz sprawdziæ pogodê:");
        countDays = getDays(scan.nextLine());
        pokazPogode(miasto, countDays);
        scan.close();
      
        
    }


    private static int getDays(String days) {
        try {
            return Integer.parseInt(days);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static String getCity(String city) throws UnsupportedEncodingException {
        return URLEncoder.encode(city, StandardCharsets.UTF_8.toString());
    }


    private static void pokazPogode(String miasto, int countDays) throws IOException, JSONException {
        if (api.getWeather(miasto, countDays)) {
        	
        	
        	
            System.out.println("\n" + "Prognoza pogody dla miasta: " + api.cityName + "\n" +
            "Aktualna temperatura w stopniach Celcjusza w tej miejscowoœci: " + api.getTemp(0) + "\n");
            
            for (int i = 0; i < api.weatherData.length; i++) {
                System.out.println( "\n" +
                        "Dzieñ: " + api.getForecastDay(i) + "\n" +
                        
                        "Przewidywana temperatura w stopniach Celcjusza: " + api.getTemp(i) + "\n" +
                        "Maksymalna temperatura w stopniach Celcjusza: " + api.getMaxTempC(i) + "\n" +
                        "Minimalna temperatura w stopniach Celcjusza: " + api.getMinTempC(i) + "\n" +
                        "Wilgotnoœæ powietrza: " + api.getHumidity(i) + "% \n" +
                		"Ciœnienie powietrza: " + api.getCisnienie(i) + " hPa \n" +
                		"Prêdkoœæ podmuchu wiatru: " + api.getWiatr(i) + " km/h \n");
            }
        } else {
            System.out.println("Niestety wystapi³ jakiœ b³¹d");
        }
    }
}
