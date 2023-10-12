package models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Users")
@NoArgsConstructor
@Data
public class User {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "Login", unique = true, nullable = false)
    private String name;

    @Column(name = "Password", nullable = false)
    private String password;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }
}
