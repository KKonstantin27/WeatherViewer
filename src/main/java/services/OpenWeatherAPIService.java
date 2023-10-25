package services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.LocationDTO;
import dto.WeatherDTO;
import models.Location;

import java.io.DataInput;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static java.time.temporal.ChronoUnit.SECONDS;

public class OpenWeatherAPIService {
    public List<LocationDTO> searchLocation(String location) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://api.openweathermap.org/geo/1.0/direct?q=" + location + "&limit=10&appid=2fb2d083824fa095846f633cf2277d2e"))
                    .timeout(Duration.of(10, SECONDS))
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String responseBody = response.body();

            ObjectMapper objectMapper = new ObjectMapper();
            int responseStatusCode = response.statusCode();
            if (responseStatusCode == 401) {

            } else if (responseStatusCode == 500) {

            }
            return objectMapper.readValue(responseBody,  new TypeReference<>(){});
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public WeatherDTO getWeatherForLocation(Location location) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.openweathermap.org/data/2.5/weather?lat=" + location.getLatitude() + "&lon=" + location.getLongitude() + "&units=metric&lang=ru&appid=2fb2d083824fa095846f633cf2277d2e"))
                    .timeout(Duration.of(10, SECONDS))
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String responseBody = response.body();
            ObjectMapper objectMapper = new ObjectMapper();
            int responseStatusCode = response.statusCode();
            if (responseStatusCode == 401) {

            } else if (responseStatusCode == 500) {

            }
            return objectMapper.readValue(responseBody, WeatherDTO.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
