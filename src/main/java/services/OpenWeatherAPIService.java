package services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.Location;

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
    public List<Location> searchLocation(String location) {
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
            JsonNode jsonNodes = objectMapper.readTree(responseBody);
            List<Location> locations = new ArrayList<>();
            int responseStatusCode = response.statusCode();
            if (responseStatusCode == 401) {

            } else if (responseStatusCode == 500) {

            }
            for (JsonNode jsonNode : jsonNodes) {
                String name = jsonNode.get("name").asText();
                double latitude = jsonNode.get("lat").asDouble();
                double longitude = jsonNode.get("lon").asDouble();
                locations.add(new Location(name, latitude, longitude));
            }
            return locations;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void getWeatherForLocation() {
        
    }
}
