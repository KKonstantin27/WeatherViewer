package dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;

@Getter
public class WeatherDTO {
    private String description;
    private String icon;
    private String temp;
    private String feels_like;
    private String pressure;
    private String humidity;
    private String visibility;
    @JsonProperty("speed")
    private String windSpeed;
    @JsonProperty("deg")
    private String windDirection;

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setTemp(String temp) {
        this.temp = temp + "°C";
    }

    public void setFeels_like(String feels_like) {
        this.feels_like = feels_like + "°C";
    }

    public void setPressure(String pressure) {
        this.pressure = pressure + "гПа";
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity +  "%";
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility + "км";
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed + "м/с";
    }

    public void setWindDirection(String windDeg) {
        this.windDirection = WindDirection.convertDegreesToDirection(Double.parseDouble(windDeg));
    }
}
