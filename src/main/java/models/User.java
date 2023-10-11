package models;

import jakarta.persistence.*;

@Entity
@Table(name = "Users")
public class User {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "Login", unique = true, nullable = false)
    private String name;

    @Column(name = "Password", nullable = false)
    private String password;
}
