package dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import models.Location;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherDTO {
    private String country;
    private String currentLocalDateTime;
    private String description;
    private String icon;
    private String temp;
    private String feels_like;
    private String min_temp;
    private String max_temp;
    private String pressure;
    private String humidity;
    private String visibility;
    private String windSpeed;
    private String windDirection;
    private String sunriseTime;
    private String sunsetTime;
    private Location location;


    public WeatherDTO(String country, int timezone, String description, String icon, String temp,
                      String feels_like, String min_temp, String max_temp, String pressure, String humidity,
                      String visibility, String windSpeed, double windDeg, long sunrise, long sunset, Location location) {
        this.country = country;
        this.currentLocalDateTime = OffsetDateTime.ofInstant(Instant.now(), ZoneOffset.ofTotalSeconds(timezone))
                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm", Locale.getDefault()));
        this.description = description;
        this.min_temp = min_temp;
        this.max_temp = max_temp;
        this.icon = icon;
        this.temp = temp;
        this.feels_like = feels_like;
        this.pressure = pressure;
        this.humidity = humidity;
        this.visibility = visibility;
        this.windSpeed = windSpeed;
        this.windDirection = WindDirection.convertDegreesToDirection(windDeg);
        this.sunriseTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(sunrise), ZoneOffset.ofTotalSeconds(timezone))
                .format(DateTimeFormatter.ofPattern("HH:mm", Locale.getDefault()));
        this.sunsetTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(sunset), ZoneOffset.ofTotalSeconds(timezone))
                .format(DateTimeFormatter.ofPattern("HH:mm", Locale.getDefault()));
        this.location = location;
    }
}

