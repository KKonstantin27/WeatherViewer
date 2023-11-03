import dto.LocationDTO;
import dto.WeatherDTO;
import exceptions.authExceptions.UserDoesNotExistException;
import exceptions.openWeaterAPIExceptions.InvalidSearchQueryException;
import exceptions.openWeaterAPIExceptions.NoResultException;
import exceptions.openWeaterAPIExceptions.OpenWeatherAPIUnavailableException;
import exceptions.openWeaterAPIExceptions.RequestLimitExceededException;
import models.Location;
import models.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import services.OpenWeatherAPIService;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

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
        Location targetLocation = new Location("Москва", testUser, 55.7504461, 37.6174943);
        WeatherDTO weatherDTO = openWeatherAPIService.getWeatherForLocation(targetLocation);
        Location locationFromWeatherDTO = weatherDTO.getLocation();
        Assertions.assertEquals(targetLocation, locationFromWeatherDTO);
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
