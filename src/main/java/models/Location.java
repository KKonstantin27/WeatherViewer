package models;

import jakarta.persistence.Table;
import jakarta.persistence.*;

@Entity
@Table(name = "Locations")
public class Location {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "Name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "userID")
    private User user;

    @Column(name = "Latitude")
    private double latitude;

    @Column(name = "Longitude")
    private double longitude;

    public Location() {
    }
}
