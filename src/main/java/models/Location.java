package models;

import jakarta.persistence.Table;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Table(name = "locations")
@Data
@AllArgsConstructor
public class Location {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "latitude")
    private double latitude;

    @Column(name = "longitude")
    private double longitude;

    public Location() {}

    public Location(String name, User user, double latitude, double longitude) {
        this.name = name;
        this.user = user;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
