import dto.LocationDTO;
import dto.WeatherDTO;
import exceptions.openWeaterAPIExceptions.InvalidSearchQueryException;
import exceptions.openWeaterAPIExceptions.NoResultException;
import exceptions.openWeaterAPIExceptions.OpenWeatherAPIUnavailableException;
import exceptions.openWeaterAPIExceptions.RequestLimitExceededException;
import models.Location;
import models.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import services.OpenWeatherAPIService;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OpenWeatherAPITest {
    private static OpenWeatherAPIService openWeatherAPIService;
    @Mock
    private static HttpClient client = mock(HttpClient.class);
    @Mock
    private static HttpResponse response = mock(HttpResponse.class);


    @BeforeAll
    public static void configureTestEnvironment() throws IOException, InterruptedException {
        openWeatherAPIService = new OpenWeatherAPIService(client);
        Mockito.when(client.send(any(), any())).thenReturn(response);
    }

    @Test
    public void testSearchLocation() throws IOException, RequestLimitExceededException, InvalidSearchQueryException, OpenWeatherAPIUnavailableException, NoResultException {
        Mockito.when(response.statusCode()).thenReturn(200);
        String locationResponseMock = Files.readString(Path.of("src/test/resources/location_response_mock.json"));
        when(response.body()).thenReturn(locationResponseMock);
        List<LocationDTO> locationDTOList = openWeatherAPIService.searchLocation("Москва");
        LocationDTO targetLocationDTO = new LocationDTO("Moscow", "RU", 55.7504461, 37.6174943);
        Assertions.assertTrue(locationDTOList.contains(targetLocationDTO));
    }

    @Test
    public void testGetWeatherForLocation() throws IOException, RequestLimitExceededException, InvalidSearchQueryException, OpenWeatherAPIUnavailableException {
        Mockito.when(response.statusCode()).thenReturn(200);
        String weatherResponseMock = Files.readString(Path.of("src/test/resources/weather_response_mock.json"));
        when(response.body()).thenReturn(weatherResponseMock);
        User testUser = new User("TestName", "TestPassword");
        Location targetLocation = new Location("Moscow", testUser, 55.7504461, 37.6174943);
        WeatherDTO weatherDTO = openWeatherAPIService.getWeatherForLocation(targetLocation);
        Assertions.assertEquals("RU", weatherDTO.getCountry());
        Assertions.assertEquals(ZonedDateTime.now(ZoneId.of("Europe/Moscow")).format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm", Locale.getDefault())),
                weatherDTO.getCurrentLocalDateTime());
        Assertions.assertEquals("небольшая облачность", weatherDTO.getDescription());
        Assertions.assertEquals("02n", weatherDTO.getIcon());
        Assertions.assertEquals("2.32", weatherDTO.getTemp());
        Assertions.assertEquals("0.3", weatherDTO.getFeels_like());
        Assertions.assertEquals("1.96", weatherDTO.getMin_temp());
        Assertions.assertEquals("3.17", weatherDTO.getMax_temp());
        Assertions.assertEquals("1014", weatherDTO.getPressure());
        Assertions.assertEquals("85", weatherDTO.getHumidity());
        Assertions.assertEquals("10000", weatherDTO.getVisibility());
        Assertions.assertEquals("1.95", weatherDTO.getWindSpeed());
        Assertions.assertEquals("западный", weatherDTO.getWindDirection());
        Assertions.assertEquals("07:38", weatherDTO.getSunriseTime());
        Assertions.assertEquals("16:47", weatherDTO.getSunsetTime());
        Assertions.assertEquals(targetLocation, weatherDTO.getLocation());
    }

    @Test
    public void searchLocationNoResultException() {
        Mockito.when(response.statusCode()).thenReturn(200);
        when(response.body()).thenReturn("[]");
        NoResultException e = assertThrows(NoResultException.class, () -> {
            openWeatherAPIService.searchLocation("");
        });
    }

    @Test
    public void searchLocationInvalidSearchQueryException404() {
        Mockito.when(response.statusCode()).thenReturn(404);
        InvalidSearchQueryException e = assertThrows(InvalidSearchQueryException.class, () -> {
            openWeatherAPIService.searchLocation("");
        });
    }

    @Test
    public void searchLocationRequestLimitExceededException429() {
        Mockito.when(response.statusCode()).thenReturn(429);
        RequestLimitExceededException e = assertThrows(RequestLimitExceededException.class, () -> {
            openWeatherAPIService.searchLocation("");
        });
    }

    @Test
    public void searchLocationOpenWeatherAPIUnavailableException500() {
        Mockito.when(response.statusCode()).thenReturn(500);
        OpenWeatherAPIUnavailableException e = assertThrows(OpenWeatherAPIUnavailableException.class, () -> {
            openWeatherAPIService.searchLocation("");
        });
    }

    @Test
    public void searchLocationOpenWeatherAPIUnavailableException502() {
        Mockito.when(response.statusCode()).thenReturn(502);
        OpenWeatherAPIUnavailableException e = assertThrows(OpenWeatherAPIUnavailableException.class, () -> {
            openWeatherAPIService.searchLocation("");
        });
    }

    @Test
    public void searchLocationOpenWeatherAPIUnavailableException503() {
        Mockito.when(response.statusCode()).thenReturn(503);
        OpenWeatherAPIUnavailableException e = assertThrows(OpenWeatherAPIUnavailableException.class, () -> {
            openWeatherAPIService.searchLocation("");
        });
    }

    @Test
    public void searchLocationOpenWeatherAPIUnavailableException504() {
        Mockito.when(response.statusCode()).thenReturn(504);
        OpenWeatherAPIUnavailableException e = assertThrows(OpenWeatherAPIUnavailableException.class, () -> {
            openWeatherAPIService.searchLocation("");
        });
    }

    @Test
    public void getWeatherForLocationInvalidSearchQueryException404() {
        Mockito.when(response.statusCode()).thenReturn(404);
        InvalidSearchQueryException e = assertThrows(InvalidSearchQueryException.class, () -> {
            openWeatherAPIService.getWeatherForLocation(new Location());
        });
    }

    @Test
    public void getWeatherForLocationRequestLimitExceededException429() {
        Mockito.when(response.statusCode()).thenReturn(429);
        RequestLimitExceededException e = assertThrows(RequestLimitExceededException.class, () -> {
            openWeatherAPIService.getWeatherForLocation(new Location());
        });
    }

    @Test
    public void getWeatherForLocationOpenWeatherAPIUnavailableException500() {
        Mockito.when(response.statusCode()).thenReturn(500);
        OpenWeatherAPIUnavailableException e = assertThrows(OpenWeatherAPIUnavailableException.class, () -> {
            openWeatherAPIService.getWeatherForLocation(new Location());
        });
    }

    @Test
    public void getWeatherForLocationOpenWeatherAPIUnavailableException502() {
        Mockito.when(response.statusCode()).thenReturn(502);
        OpenWeatherAPIUnavailableException e = assertThrows(OpenWeatherAPIUnavailableException.class, () -> {
            openWeatherAPIService.getWeatherForLocation(new Location());
        });
    }

    @Test
    public void getWeatherForLocationOpenWeatherAPIUnavailableException503() {
        Mockito.when(response.statusCode()).thenReturn(503);
        OpenWeatherAPIUnavailableException e = assertThrows(OpenWeatherAPIUnavailableException.class, () -> {
            openWeatherAPIService.getWeatherForLocation(new Location());
        });
    }

    @Test
    public void getWeatherForLocationOpenWeatherAPIUnavailableException504() {
        Mockito.when(response.statusCode()).thenReturn(504);
        OpenWeatherAPIUnavailableException e = assertThrows(OpenWeatherAPIUnavailableException.class, () -> {
            openWeatherAPIService.getWeatherForLocation(new Location());
        });
    }
}
