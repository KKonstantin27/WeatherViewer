package models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.id.GUIDGenerator;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "sessions")
@NoArgsConstructor
@Data
public class UserSession {

    @Id
    @Column(name = "id")
    private String id;

    @ManyToOne
    @JoinColumn (name = "user_id")
    private User user;

    @Column(name = "expires_at")
    private LocalDateTime expiresAt;

    public UserSession(User user) {
        this.id = UUID.randomUUID().toString();
        this.user = user;
        this.expiresAt = LocalDateTime.now().plusHours(4);
    }
}
