package models;

import dao.UserSessionDAO;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Entity
@Table(name = "sessions")
@NoArgsConstructor
@Data
public class UserSession {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    private String id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "expires_at", nullable = false)
    private ZonedDateTime expiresAt;

    public UserSession(User user) {
        this.id = UUID.randomUUID().toString();
        this.user = user;
        this.expiresAt = ZonedDateTime.now(ZoneId.of("UTC")).plusHours(8);
    }

    public void updateExpiresAt() {
        this.expiresAt = expiresAt.plusHours(8);
    }

    public void clearOldSessions() {
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(1);
        Runnable task = () -> {
            UserSessionDAO userSessionDAO = new UserSessionDAO();
            userSessionDAO.delete();
        };
        pool.scheduleAtFixedRate(task, 0, 8, TimeUnit.HOURS);
    }
}
