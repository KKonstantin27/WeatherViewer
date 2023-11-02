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

    private static long SESSION_DURATION_IN_MINUTES = 480;
    private static long OLD_SESSIONS_CLEANING_PERIOD = 480;

    public UserSession(User user) {
        this.id = UUID.randomUUID().toString();
        this.user = user;
        this.expiresAt = ZonedDateTime.now(ZoneId.of("UTC")).plusMinutes(SESSION_DURATION_IN_MINUTES);
    }

    public void updateExpiresAt() {
        this.expiresAt = ZonedDateTime.now(ZoneId.of("UTC")).plusMinutes(SESSION_DURATION_IN_MINUTES);
    }

    public static void clearOldSessions() {
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(1);
        Runnable task = () -> {
            UserSessionDAO userSessionDAO = new UserSessionDAO();
            userSessionDAO.delete();
        };
        pool.scheduleAtFixedRate(task, OLD_SESSIONS_CLEANING_PERIOD, OLD_SESSIONS_CLEANING_PERIOD, TimeUnit.MINUTES);
    }

    public static void setSessionDurationInMinutes(long sessionDurationInMinutes) {
        SESSION_DURATION_IN_MINUTES = sessionDurationInMinutes;
    }

    public static void setOldSessionsCleaningPeriod(long oldSessionsCleaningPeriod) {
        OLD_SESSIONS_CLEANING_PERIOD = oldSessionsCleaningPeriod;
    }
}
