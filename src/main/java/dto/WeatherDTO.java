package dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import models.Location;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherDTO {
    private String name;
    private String country;
    private String description;
    private String icon;
    private String temp;
    private String feels_like;
    private String pressure;
    private String humidity;
    private String visibility;
    private Location location;
    @JsonProperty("speed")
    private String windSpeed;
    @JsonProperty("deg")
    private String windDirection;

    public WeatherDTO(String name, String country, String description, String icon, String temp, String feels_like,
                      String pressure, String humidity, String visibility, String windSpeed, double windDeg, Location location) {
        this.name = name;
        this.country = country;
        this.description = description;
        this.icon = icon;
        this.temp = temp;
        this.feels_like = feels_like;
        this.pressure = pressure;
        this.humidity = humidity;
        this.visibility = visibility;
        this.windSpeed = windSpeed;
        this.windDirection = WindDirection.convertDegreesToDirection(windDeg);
        this.location = location;
    }
}

