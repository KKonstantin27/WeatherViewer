package services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.LocationDTO;
import dto.WeatherDTO;
import exceptions.openWeaterAPIExceptions.InvalidSearchQueryException;
import exceptions.openWeaterAPIExceptions.OpenWeatherAPIUnavailableException;
import exceptions.openWeaterAPIExceptions.RequestLimitExceededException;
import models.Location;

import java.io.IOException;
import java.net.URI;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;

import static java.time.temporal.ChronoUnit.SECONDS;

public class OpenWeatherAPIService {
    private ObjectMapper objectMapper = new ObjectMapper();

    public List<LocationDTO> searchLocation(String location) throws OpenWeatherAPIUnavailableException, InvalidSearchQueryException, RequestLimitExceededException {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://api.openweathermap.org/geo/1.0/direct?q=" + location + "&limit=10&appid=2fb2d083824fa095846f633cf2277d2e"))
                    .timeout(Duration.of(10, SECONDS))
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String responseBody = response.body();
            checkResponseStatusCode(response.statusCode());
            return objectMapper.readValue(responseBody, new TypeReference<>() {
            });
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public WeatherDTO getWeatherForLocation(Location location) throws OpenWeatherAPIUnavailableException, InvalidSearchQueryException, RequestLimitExceededException {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.openweathermap.org/data/2.5/weather?lat=" + location.getLatitude() + "&lon=" + location.getLongitude() + "&units=metric&lang=ru&appid=2fb2d083824fa095846f633cf2277d2e"))
                    .timeout(Duration.of(10, SECONDS))
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String responseBody = response.body();

            JsonNode jsonNode = objectMapper.readTree(responseBody);
            checkResponseStatusCode(response.statusCode());

            WeatherDTO weatherDTO = new WeatherDTO(
                    jsonNode.get("name").asText(),
                    jsonNode.get("sys").get("country").asText(),
                    jsonNode.get("weather").get(0).get("description").asText(),
                    jsonNode.get("weather").get(0).get("icon").asText(),
                    jsonNode.get("main").get("temp").asText(),
                    jsonNode.get("main").get("feels_like").asText(),
                    jsonNode.get("main").get("pressure").asText(),
                    jsonNode.get("main").get("humidity").asText(),
                    jsonNode.get("visibility").asText(),
                    jsonNode.get("wind").get("speed").asText(),
                    jsonNode.get("wind").get("deg").asDouble(),
                    location);
            return weatherDTO;
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    private void checkResponseStatusCode(int responseStatusCode) throws OpenWeatherAPIUnavailableException, InvalidSearchQueryException, RequestLimitExceededException {
        if (responseStatusCode == 404) {
            throw new InvalidSearchQueryException("Некорректный поисковый запрос");
        } else if (responseStatusCode == 409) {
            throw new RequestLimitExceededException("Превышен лимит запросов к OpenWeatherAPI, попробуйте позже");
        } else if (responseStatusCode >= 500 && responseStatusCode <= 504) {
            throw new OpenWeatherAPIUnavailableException("Сервис временно недоступен, попробуйте позже");
        }
    }
}
