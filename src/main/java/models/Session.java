package models;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Sessions")
public class Session {

    @Id
    @Column(name = "ID")
    private String id;

    @ManyToOne
    @Column (name = "userID")
    private User user;

    @Column(name = "ExpiresAt")
    private Date expiresAt;
}
