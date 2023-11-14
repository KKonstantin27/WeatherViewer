package models;

import dao.UserSessionDAO;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZoneId;
import java.time.ZonedDateTime;
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
    private static UserSessionDAO userSessionDAO = new UserSessionDAO();
    private static ScheduledExecutorService pool = Executors.newScheduledThreadPool(1);
    private static long sessionDurationInMinutes = 480;
    private static long oldSessionsCleaningPeriod = 480;

    public UserSession(User user) {
        this.id = UUID.randomUUID().toString();
        this.user = user;
        this.expiresAt = ZonedDateTime.now(ZoneId.of("UTC")).plusMinutes(sessionDurationInMinutes);
    }

    public void updateExpiresAt() {
        this.expiresAt = ZonedDateTime.now(ZoneId.of("UTC")).plusMinutes(sessionDurationInMinutes);
    }

    public static void clearOldSessions() {
        Runnable task = () -> {
            userSessionDAO.delete();
        };
        pool.scheduleAtFixedRate(task, oldSessionsCleaningPeriod, oldSessionsCleaningPeriod, TimeUnit.MINUTES);
    }

    public static void setSessionDurationInMinutes(long sessionDurationInMinutesForTest) {
        sessionDurationInMinutes = sessionDurationInMinutesForTest;
    }


}
