package models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
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
    private ZonedDateTime expiresAt;

    public UserSession(User user) {
        this.id = UUID.randomUUID().toString();
        this.user = user;
        this.expiresAt = ZonedDateTime.now(ZoneId.of("UTC")).plusHours(8);
    }

    public void updateExpiresAt() {
        this.expiresAt = expiresAt.plusHours(8);
    }
}
