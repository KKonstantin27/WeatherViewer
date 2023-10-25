package dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class LocationDTO {
    private String name;
    private String country;
    @JsonProperty("lat")
    private double latitude;
    @JsonProperty("lon")
    private double longitude;

    @Override
    public String toString() {
        return name + ", код страны: " + country + ", координаты: " + latitude + ", " + longitude;
    }
}
